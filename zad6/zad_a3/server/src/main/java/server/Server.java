package server;

import io.grpc.Context;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import proto.OfficeGrpc;
import proto.OfficeProto.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class Builders {
    private static Case case_(int caseID) {
        return Case.newBuilder().setId(caseID).build();
    }

    private static CaseAck caseResultAck(int cid, int resolutionTime) {
        return CaseAck.newBuilder()
                .setCase(case_(cid))
                .setResolutionTime(resolutionTime)
                .build();
    }

    private static CaseResult caseResult(int cid, String message) {
        return CaseResult.newBuilder()
                .setCase(case_(cid))
                .setMessage(message)
                .build();
    }

    public static RegisterCaseResult registerCaseAck(int cid, int resolutionTime) {
        return RegisterCaseResult.newBuilder()
                .setCaseAck(caseResultAck(cid, resolutionTime))
                .build();
    }

    public static RegisterCaseResult registerCaseResult(int cid, String message) {
        return RegisterCaseResult.newBuilder()
                .setCaseResult(caseResult(cid, message))
                .build();
    }

    public static CaseResultSequence caseResultSequence(List<CaseResult> results) {
        return CaseResultSequence.newBuilder()
                .addAllResults(results == null ? List.of() : results)
                .build();
    }
}

@Slf4j
class WorkerService {
    private final AtomicInteger caseID = new AtomicInteger(0);
    private final ExecutorService workers = Executors.newFixedThreadPool(1); //TODO: increase this
    private final ConcurrentHashMap<UUID, StreamObserver<RegisterCaseResult>> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, List<CaseResult>> readyCases = new ConcurrentHashMap<>();

    private void processRequest(Context ctx, UUID uuid, int cid, int estimatedTime, Supplier<RegisterCaseResult> resultFn){
        workers.submit(() -> {
            log.info("Working on case: " + cid);
            workHard(estimatedTime);
            log.info("Case " + cid + " ready");

            var caseEndedResponse = resultFn.get();

            if (ctx.isCancelled()) { // save to cache
                log.info(String.format("Client %s not present - saving case %d to cache", uuid, cid));
                var caseResult = caseEndedResponse.getCaseResult();

                readyCases.computeIfAbsent(uuid, k -> new LinkedList<>());
                readyCases.get(uuid).add(caseResult);

                clients.remove(uuid); // client closed stream, remove him from clients map
            } else { // send response
                // get observer from map, because it can be updated in meantime
                var storedObserver = clients.get(uuid);
                storedObserver.onNext(caseEndedResponse);
            }
        });
    }

    public void updateClient(Client client, StreamObserver<RegisterCaseResult> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        log.info("Client " + uuid + " connected");
        clients.put(uuid, responseObserver);
        responseObserver.onNext(Builders.registerCaseResult(1, "asd"));
    }

    public void driverLicenseRequest(DriverLicenseData data, StreamObserver<RegisterCaseResult> responseObserver) {
        var client = data.getClient();
        var uuid = UUID.fromString(client.getClientID());
        clients.put(uuid, responseObserver);

        var cid = caseID.incrementAndGet();
        var estimatedTime = new Random().nextInt(2000) + 5000;
        log.info("driverLicenseRequest " + uuid + " estimatedTime=" + estimatedTime);
        responseObserver.onNext(Builders.registerCaseAck(cid, estimatedTime));

        Supplier<RegisterCaseResult> resultCallable = () -> {
            var category = data.getCategory();
            var message = new Random().nextBoolean() ?
                    String.format("Driver license granted [%s]", category) :
                    "We are sorry, but you have to send additional documents";
            return Builders.registerCaseResult(cid, message);
        };

        var ctx = Context.current();
        processRequest(ctx, uuid, cid, estimatedTime, resultCallable);
    }

    public void getExaminedCases(Client client, StreamObserver<CaseResultSequence> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        var results = readyCases.remove(uuid);
        responseObserver.onNext(Builders.caseResultSequence(results));
        responseObserver.onCompleted();
    }

    private void workHard(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        workers.shutdownNow();
    }
}

@Slf4j
class ReceptionImpl extends OfficeGrpc.OfficeImplBase {
    private final WorkerService workerService = new WorkerService();

    @Override
    public StreamObserver<CaseRequestData> caseRequest(StreamObserver<RegisterCaseResult> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(CaseRequestData value) {
                System.out.println(value);
                switch (value.getTypeCase()) {
                    case DRIVERLICENSEDATA -> workerService.driverLicenseRequest(value.getDriverLicenseData(), responseObserver);
//                    case IDCARDDATA -> {
                        //
//                    }
//                    case REGISTERCOMPANYDATA -> {
                        //
//                    }
                    case CLIENTREADY -> workerService.updateClient(value.getClientReady().getClient(), responseObserver);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error " + t.getMessage());
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void helloRequest(Client request, StreamObserver<CaseResultSequence> responseObserver) {
        workerService.getExaminedCases(request, responseObserver);
    }

    void stop() {
        workerService.shutdown();
    }
}


@Slf4j
class grpcServer {
    private static final int PORT = 50051;

    public static void main(String[] args) throws InterruptedException, IOException {
        var reception = new ReceptionImpl();
        var server = ServerBuilder/*NettyServerBuilder*/
                .forPort(PORT)
                .executor(Executors.newFixedThreadPool(4))
                .addService(reception)
                .build()
                .start();

        log.info("Server started, listening on " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            reception.stop();
            System.err.println("*** server shut down");
        }));

        server.awaitTermination();
    }
}
