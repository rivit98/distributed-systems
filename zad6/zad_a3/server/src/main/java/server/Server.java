package server;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import proto.OfficeGrpc;
import proto.OfficeProto.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class Builders {
    private static Case case_(int caseID) {
        return Case.newBuilder().setId(caseID).build();
    }

    private static CaseAck caseAck(int resolutionTime) {
        return CaseAck.newBuilder()
                .setResolutionTime(resolutionTime)
                .build();
    }

    private static CaseResolvedResult caseResolvedResult(String message) {
        return CaseResolvedResult.newBuilder()
                .setMessage(message)
                .build();
    }

    public static CaseResult caseResult(int cid, int resolutionTime) {
        return CaseResult.newBuilder()
                .setCase(case_(cid))
                .setCaseAck(caseAck(resolutionTime))
                .build();
    }

    public static CaseResult caseResult(int cid, String message) {
        return CaseResult.newBuilder()
                .setCase(case_(cid))
                .setCaseResolvedResult(caseResolvedResult(message))
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
    private final ConcurrentHashMap<UUID, StreamObserver<CaseResult>> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, LinkedList<CaseResult>> readyCases = new ConcurrentHashMap<>();

    private void processRequest(UUID uuid, int cid, int estimatedTime, Supplier<CaseResult> resultFn) {
        workers.submit(() -> {
            log.info("Working on case: " + cid);
            workHard(estimatedTime);
            log.info("Case " + cid + " ready");

            var caseEndedResponse = resultFn.get();
            if (clients.get(uuid) == null) { // save to cache
                log.info(String.format("Client %s not present - saving case %d to cache", uuid, cid));
                readyCases.computeIfAbsent(uuid, k -> new LinkedList<>());
                readyCases.get(uuid).add(caseEndedResponse);
            } else { // send response
                // get observer from map, because it can be updated in meantime
                var storedObserver = clients.get(uuid);
                storedObserver.onNext(caseEndedResponse);
            }
        });
    }

    private int getEstimatedTime() {
        return new Random().nextInt(2000) + 2000;
    }

    private boolean checkAge(String date, int age){
        try {
            var format = new SimpleDateFormat("dd/MM/yyyy");
            var birthdate = format.parse(date);
            var localDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            var period = Period.between(localDate, LocalDate.now());
            var years = period.get(ChronoUnit.YEARS);
            if(years >= age){
                return true;
            }
        } catch (ParseException ignored) {
        }

        return false;
    }

    public void driverLicenseRequest(Client client, DriverLicenseData data, StreamObserver<CaseResult> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        var cid = caseID.incrementAndGet();
        var estimatedTime = getEstimatedTime();
        log.info("driverLicenseRequest " + uuid + " estimatedTime=" + estimatedTime);
        responseObserver.onNext(Builders.caseResult(cid, estimatedTime));

        Supplier<CaseResult> resultCallable = () -> {
            var category = data.getCategory();
            var birthDate = data.getBirthDate();
            var over18 = checkAge(birthDate, 18);

            var messages = new String[] {
                    String.format("Driver license granted [%s]", category),
                    "You are too young",
                    "You have to send additional documents"
            };

            var messageIdx = 0;
            if(!over18){
                messageIdx = 1;
            }else if(new Random().nextBoolean()){
                messageIdx = 2;
            }

            return Builders.caseResult(cid, messages[messageIdx]);
        };

        processRequest(uuid, cid, estimatedTime, resultCallable);
    }

    public void idCardRequest(Client client, IDCardData data, StreamObserver<CaseResult> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        var cid = caseID.incrementAndGet();
        var estimatedTime = getEstimatedTime();
        log.info("idCardRequest " + uuid + " estimatedTime=" + estimatedTime);
        responseObserver.onNext(Builders.caseResult(cid, estimatedTime));

        Supplier<CaseResult> resultCallable = () -> {
            var birthDate = data.getBirthDate();
            var placeOfResidence = data.getPlaceOfResidence();
            var over15 = checkAge(birthDate, 15);

            var messages = new String[] {
                    String.format("ID card prepared. You can pick it up in your place of residence [%s]", placeOfResidence),
                    "You are too young",
                    "You have to send additional documents"
            };

            var messageIdx = 0;
            if(!over15){
                messageIdx = 1;
            }else if(new Random().nextBoolean()){
                messageIdx = 2;
            }

            return Builders.caseResult(cid, messages[messageIdx]);
        };

        processRequest(uuid, cid, estimatedTime, resultCallable);
    }

    public void registerCompanyRequest(Client client, RegisterCompanyData data, StreamObserver<CaseResult> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        var cid = caseID.incrementAndGet();
        var estimatedTime = getEstimatedTime();
        log.info("registerCompanyRequest " + uuid + " estimatedTime=" + estimatedTime);
        responseObserver.onNext(Builders.caseResult(cid, estimatedTime));

        Supplier<CaseResult> resultCallable = () -> {
            var companyName = data.getCompanyName();
            var startUpCapital = data.getStartUpCapital();
            var message = new Random().nextBoolean() ?
                    String.format("Company [%s] registered!", companyName) :
                    String.format("We are sorry, but your initial capital [%.2fzl] is too small :(", startUpCapital);
            return Builders.caseResult(cid, message);
        };

        processRequest(uuid, cid, estimatedTime, resultCallable);
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
        } catch (InterruptedException ignored) {
        }
    }

    public void shutdown() {
        workers.shutdownNow();
    }

    public void clientConnected(Client client, StreamObserver<CaseResult> responseObserver) {
        var uuid = UUID.fromString(client.getClientID());
        log.info("Client " + uuid + " connected");
        clients.put(uuid, responseObserver);
    }

    public void clientDisconnected(Client client) {
        var uuid = UUID.fromString(client.getClientID());
        log.info("Client " + uuid + " disconnected");
        clients.remove(uuid);
    }
}

@Slf4j
class ReceptionImpl extends OfficeGrpc.OfficeImplBase {
    private final WorkerService workerService = new WorkerService();

    @Override
    public StreamObserver<CaseRequestData> caseRequest(StreamObserver<CaseResult> responseObserver) {
        return new StreamObserver<>() {
            private Client client;

            @Override
            public void onNext(CaseRequestData value) {
                client = value.getClient();
                switch (value.getTypeCase()) {
                    case DRIVERLICENSEDATA -> workerService.driverLicenseRequest(client, value.getDriverLicenseData(), responseObserver);
                    case IDCARDDATA -> workerService.idCardRequest(client, value.getIdCardData(), responseObserver);
                    case REGISTERCOMPANYDATA -> workerService.registerCompanyRequest(client, value.getRegisterCompanyData(), responseObserver);
                    case CLIENTREADY -> workerService.clientConnected(client, responseObserver);
                }
            }

            @Override
            public void onError(Throwable t) {
//                t.printStackTrace();
                workerService.clientDisconnected(client);
                client = null;
            }

            @Override
            public void onCompleted() {
                workerService.clientDisconnected(client);
                responseObserver.onCompleted();
                client = null;
            }
        };
    }

    @Override
    public void getResolvedCases(Client request, StreamObserver<CaseResultSequence> responseObserver) {
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
        var server =
                NettyServerBuilder
                        .forPort(PORT)
                        .executor(Executors.newFixedThreadPool(4))
                        .addService(reception)
                        .permitKeepAliveWithoutCalls(true)
                        .permitKeepAliveTime(10, TimeUnit.SECONDS)
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
