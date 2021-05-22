package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.ArithmeticMultArguments;
import sr.grpc.gen.ArithmeticOpResult;
import sr.grpc.gen.CalculatorGrpc.CalculatorImplBase;

public class CalculatorImpl extends CalculatorImplBase {
    @Override
    public void add(sr.grpc.gen.ArithmeticOpArguments request,
                    io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
        System.out.println("addRequest (" + request.getArg1() + ", " + request.getArg2() + ")");
        int val = request.getArg1() + request.getArg2();
        ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
        if (request.getArg1() > 100 && request.getArg2() > 100) try {
            Thread.sleep(5000);
        } catch (java.lang.InterruptedException ex) {
        }
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(sr.grpc.gen.ArithmeticOpArguments request,
                         io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
        System.out.println("subtractRequest (" + request.getArg1() + ", " + request.getArg2() + ")");
        int val = request.getArg1() - request.getArg2();
        ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void mult(ArithmeticMultArguments request, StreamObserver<ArithmeticOpResult> responseObserver) {
        var list = request.getArgsList();
        System.out.println("mult (" + list + ")");
        var multRes = list.stream().reduce(1, (acc, v) -> acc * v);
        var result = ArithmeticOpResult.newBuilder().setRes(multRes).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
