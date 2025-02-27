package sr.grpc.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: calculator.proto")
public final class CalculatorGrpc {

  private CalculatorGrpc() {}

  public static final String SERVICE_NAME = "calculator.Calculator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments,
      sr.grpc.gen.ArithmeticOpResult> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Add",
      requestType = sr.grpc.gen.ArithmeticOpArguments.class,
      responseType = sr.grpc.gen.ArithmeticOpResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments,
      sr.grpc.gen.ArithmeticOpResult> getAddMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments, sr.grpc.gen.ArithmeticOpResult> getAddMethod;
    if ((getAddMethod = CalculatorGrpc.getAddMethod) == null) {
      synchronized (CalculatorGrpc.class) {
        if ((getAddMethod = CalculatorGrpc.getAddMethod) == null) {
          CalculatorGrpc.getAddMethod = getAddMethod =
              io.grpc.MethodDescriptor.<sr.grpc.gen.ArithmeticOpArguments, sr.grpc.gen.ArithmeticOpResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticOpArguments.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticOpResult.getDefaultInstance()))
              .setSchemaDescriptor(new CalculatorMethodDescriptorSupplier("Add"))
              .build();
        }
      }
    }
    return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments,
      sr.grpc.gen.ArithmeticOpResult> getSubtractMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Subtract",
      requestType = sr.grpc.gen.ArithmeticOpArguments.class,
      responseType = sr.grpc.gen.ArithmeticOpResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments,
      sr.grpc.gen.ArithmeticOpResult> getSubtractMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticOpArguments, sr.grpc.gen.ArithmeticOpResult> getSubtractMethod;
    if ((getSubtractMethod = CalculatorGrpc.getSubtractMethod) == null) {
      synchronized (CalculatorGrpc.class) {
        if ((getSubtractMethod = CalculatorGrpc.getSubtractMethod) == null) {
          CalculatorGrpc.getSubtractMethod = getSubtractMethod =
              io.grpc.MethodDescriptor.<sr.grpc.gen.ArithmeticOpArguments, sr.grpc.gen.ArithmeticOpResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Subtract"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticOpArguments.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticOpResult.getDefaultInstance()))
              .setSchemaDescriptor(new CalculatorMethodDescriptorSupplier("Subtract"))
              .build();
        }
      }
    }
    return getSubtractMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticMultArguments,
      sr.grpc.gen.ArithmeticOpResult> getMultMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Mult",
      requestType = sr.grpc.gen.ArithmeticMultArguments.class,
      responseType = sr.grpc.gen.ArithmeticOpResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticMultArguments,
      sr.grpc.gen.ArithmeticOpResult> getMultMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.ArithmeticMultArguments, sr.grpc.gen.ArithmeticOpResult> getMultMethod;
    if ((getMultMethod = CalculatorGrpc.getMultMethod) == null) {
      synchronized (CalculatorGrpc.class) {
        if ((getMultMethod = CalculatorGrpc.getMultMethod) == null) {
          CalculatorGrpc.getMultMethod = getMultMethod =
              io.grpc.MethodDescriptor.<sr.grpc.gen.ArithmeticMultArguments, sr.grpc.gen.ArithmeticOpResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Mult"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticMultArguments.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.ArithmeticOpResult.getDefaultInstance()))
              .setSchemaDescriptor(new CalculatorMethodDescriptorSupplier("Mult"))
              .build();
        }
      }
    }
    return getMultMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CalculatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorStub>() {
        @java.lang.Override
        public CalculatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorStub(channel, callOptions);
        }
      };
    return CalculatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CalculatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorBlockingStub>() {
        @java.lang.Override
        public CalculatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorBlockingStub(channel, callOptions);
        }
      };
    return CalculatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CalculatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalculatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalculatorFutureStub>() {
        @java.lang.Override
        public CalculatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalculatorFutureStub(channel, callOptions);
        }
      };
    return CalculatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CalculatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void add(sr.grpc.gen.ArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddMethod(), responseObserver);
    }

    /**
     */
    public void subtract(sr.grpc.gen.ArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubtractMethod(), responseObserver);
    }

    /**
     */
    public void mult(sr.grpc.gen.ArithmeticMultArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMultMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ArithmeticOpArguments,
                sr.grpc.gen.ArithmeticOpResult>(
                  this, METHODID_ADD)))
          .addMethod(
            getSubtractMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ArithmeticOpArguments,
                sr.grpc.gen.ArithmeticOpResult>(
                  this, METHODID_SUBTRACT)))
          .addMethod(
            getMultMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ArithmeticMultArguments,
                sr.grpc.gen.ArithmeticOpResult>(
                  this, METHODID_MULT)))
          .build();
    }
  }

  /**
   */
  public static final class CalculatorStub extends io.grpc.stub.AbstractAsyncStub<CalculatorStub> {
    private CalculatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorStub(channel, callOptions);
    }

    /**
     */
    public void add(sr.grpc.gen.ArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void subtract(sr.grpc.gen.ArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubtractMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mult(sr.grpc.gen.ArithmeticMultArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMultMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CalculatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<CalculatorBlockingStub> {
    private CalculatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public sr.grpc.gen.ArithmeticOpResult add(sr.grpc.gen.ArithmeticOpArguments request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddMethod(), getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.ArithmeticOpResult subtract(sr.grpc.gen.ArithmeticOpArguments request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubtractMethod(), getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.ArithmeticOpResult mult(sr.grpc.gen.ArithmeticMultArguments request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMultMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CalculatorFutureStub extends io.grpc.stub.AbstractFutureStub<CalculatorFutureStub> {
    private CalculatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalculatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ArithmeticOpResult> add(
        sr.grpc.gen.ArithmeticOpArguments request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ArithmeticOpResult> subtract(
        sr.grpc.gen.ArithmeticOpArguments request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubtractMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ArithmeticOpResult> mult(
        sr.grpc.gen.ArithmeticMultArguments request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMultMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_SUBTRACT = 1;
  private static final int METHODID_MULT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CalculatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CalculatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          serviceImpl.add((sr.grpc.gen.ArithmeticOpArguments) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult>) responseObserver);
          break;
        case METHODID_SUBTRACT:
          serviceImpl.subtract((sr.grpc.gen.ArithmeticOpArguments) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult>) responseObserver);
          break;
        case METHODID_MULT:
          serviceImpl.mult((sr.grpc.gen.ArithmeticMultArguments) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CalculatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CalculatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.CalculatorProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Calculator");
    }
  }

  private static final class CalculatorFileDescriptorSupplier
      extends CalculatorBaseDescriptorSupplier {
    CalculatorFileDescriptorSupplier() {}
  }

  private static final class CalculatorMethodDescriptorSupplier
      extends CalculatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CalculatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CalculatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CalculatorFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getSubtractMethod())
              .addMethod(getMultMethod())
              .build();
        }
      }
    }
    return result;
  }
}
