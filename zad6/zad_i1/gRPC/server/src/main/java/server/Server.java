package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import proto.TesterGrpc;
import proto.TesterProto;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;


class TesterImpl extends TesterGrpc.TesterImplBase{
    private static final TesterProto.Empty empty = TesterProto.Empty.newBuilder().build();

    private static <T> void iterateAndIgnore(List<T> arr){
        long i = 0;
        for(T v : arr){
            i++;
        }
    }

    void endRequest(StreamObserver<TesterProto.Empty> responseObserver){
        if(responseObserver != null){
            responseObserver.onNext(empty);
            responseObserver.onCompleted();
            System.out.println("req completed");
        }
    }

    @Override
    public void processSmall(TesterProto.SmallData request, StreamObserver<TesterProto.Empty> responseObserver) {
        iterateAndIgnore(request.getISeq1List());
        endRequest(responseObserver);
    }

    @Override
    public void processMedium(TesterProto.MediumData request, StreamObserver<TesterProto.Empty> responseObserver) {
        processSmall(request.getSmallData(), null);

        iterateAndIgnore(request.getSSeq1List());
        endRequest(responseObserver);
    }

    @Override
    public void processBig(TesterProto.BigData request, StreamObserver<TesterProto.Empty> responseObserver) {
        processMedium(request.getMediumData(), null);

        iterateAndIgnore(request.getISeq2List());
        iterateAndIgnore(request.getSSeq2List());
        iterateAndIgnore(request.getDSeq1List());
        iterateAndIgnore(request.getDSeq2List());
        endRequest(responseObserver);
    }
}


class grpcServer {
    private static final int PORT = 50051;
    private Server server;


    public static void main(String[] args) throws InterruptedException, IOException {
        var server = new grpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        server = ServerBuilder/*NettyServerBuilder*/.forPort(PORT).executor(Executors.newFixedThreadPool(4))
                .addService(new TesterImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            grpcServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
