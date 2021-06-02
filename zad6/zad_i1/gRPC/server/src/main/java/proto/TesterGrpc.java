package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: Tester.proto")
public final class TesterGrpc {

  private TesterGrpc() {}

  public static final String SERVICE_NAME = "tester.Tester";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.TesterProto.SmallData,
      proto.TesterProto.Empty> getProcessSmallMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "processSmall",
      requestType = proto.TesterProto.SmallData.class,
      responseType = proto.TesterProto.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.TesterProto.SmallData,
      proto.TesterProto.Empty> getProcessSmallMethod() {
    io.grpc.MethodDescriptor<proto.TesterProto.SmallData, proto.TesterProto.Empty> getProcessSmallMethod;
    if ((getProcessSmallMethod = TesterGrpc.getProcessSmallMethod) == null) {
      synchronized (TesterGrpc.class) {
        if ((getProcessSmallMethod = TesterGrpc.getProcessSmallMethod) == null) {
          TesterGrpc.getProcessSmallMethod = getProcessSmallMethod =
              io.grpc.MethodDescriptor.<proto.TesterProto.SmallData, proto.TesterProto.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "processSmall"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.SmallData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TesterMethodDescriptorSupplier("processSmall"))
              .build();
        }
      }
    }
    return getProcessSmallMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.TesterProto.MediumData,
      proto.TesterProto.Empty> getProcessMediumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "processMedium",
      requestType = proto.TesterProto.MediumData.class,
      responseType = proto.TesterProto.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.TesterProto.MediumData,
      proto.TesterProto.Empty> getProcessMediumMethod() {
    io.grpc.MethodDescriptor<proto.TesterProto.MediumData, proto.TesterProto.Empty> getProcessMediumMethod;
    if ((getProcessMediumMethod = TesterGrpc.getProcessMediumMethod) == null) {
      synchronized (TesterGrpc.class) {
        if ((getProcessMediumMethod = TesterGrpc.getProcessMediumMethod) == null) {
          TesterGrpc.getProcessMediumMethod = getProcessMediumMethod =
              io.grpc.MethodDescriptor.<proto.TesterProto.MediumData, proto.TesterProto.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "processMedium"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.MediumData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TesterMethodDescriptorSupplier("processMedium"))
              .build();
        }
      }
    }
    return getProcessMediumMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.TesterProto.BigData,
      proto.TesterProto.Empty> getProcessBigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "processBig",
      requestType = proto.TesterProto.BigData.class,
      responseType = proto.TesterProto.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.TesterProto.BigData,
      proto.TesterProto.Empty> getProcessBigMethod() {
    io.grpc.MethodDescriptor<proto.TesterProto.BigData, proto.TesterProto.Empty> getProcessBigMethod;
    if ((getProcessBigMethod = TesterGrpc.getProcessBigMethod) == null) {
      synchronized (TesterGrpc.class) {
        if ((getProcessBigMethod = TesterGrpc.getProcessBigMethod) == null) {
          TesterGrpc.getProcessBigMethod = getProcessBigMethod =
              io.grpc.MethodDescriptor.<proto.TesterProto.BigData, proto.TesterProto.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "processBig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.BigData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.TesterProto.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new TesterMethodDescriptorSupplier("processBig"))
              .build();
        }
      }
    }
    return getProcessBigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TesterStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TesterStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TesterStub>() {
        @java.lang.Override
        public TesterStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TesterStub(channel, callOptions);
        }
      };
    return TesterStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TesterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TesterBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TesterBlockingStub>() {
        @java.lang.Override
        public TesterBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TesterBlockingStub(channel, callOptions);
        }
      };
    return TesterBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TesterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TesterFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TesterFutureStub>() {
        @java.lang.Override
        public TesterFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TesterFutureStub(channel, callOptions);
        }
      };
    return TesterFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TesterImplBase implements io.grpc.BindableService {

    /**
     */
    public void processSmall(proto.TesterProto.SmallData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessSmallMethod(), responseObserver);
    }

    /**
     */
    public void processMedium(proto.TesterProto.MediumData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessMediumMethod(), responseObserver);
    }

    /**
     */
    public void processBig(proto.TesterProto.BigData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessBigMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getProcessSmallMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                proto.TesterProto.SmallData,
                proto.TesterProto.Empty>(
                  this, METHODID_PROCESS_SMALL)))
          .addMethod(
            getProcessMediumMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                proto.TesterProto.MediumData,
                proto.TesterProto.Empty>(
                  this, METHODID_PROCESS_MEDIUM)))
          .addMethod(
            getProcessBigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                proto.TesterProto.BigData,
                proto.TesterProto.Empty>(
                  this, METHODID_PROCESS_BIG)))
          .build();
    }
  }

  /**
   */
  public static final class TesterStub extends io.grpc.stub.AbstractAsyncStub<TesterStub> {
    private TesterStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TesterStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TesterStub(channel, callOptions);
    }

    /**
     */
    public void processSmall(proto.TesterProto.SmallData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessSmallMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void processMedium(proto.TesterProto.MediumData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessMediumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void processBig(proto.TesterProto.BigData request,
        io.grpc.stub.StreamObserver<proto.TesterProto.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessBigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TesterBlockingStub extends io.grpc.stub.AbstractBlockingStub<TesterBlockingStub> {
    private TesterBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TesterBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TesterBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.TesterProto.Empty processSmall(proto.TesterProto.SmallData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessSmallMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.TesterProto.Empty processMedium(proto.TesterProto.MediumData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessMediumMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.TesterProto.Empty processBig(proto.TesterProto.BigData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessBigMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TesterFutureStub extends io.grpc.stub.AbstractFutureStub<TesterFutureStub> {
    private TesterFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TesterFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TesterFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.TesterProto.Empty> processSmall(
        proto.TesterProto.SmallData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessSmallMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.TesterProto.Empty> processMedium(
        proto.TesterProto.MediumData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessMediumMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.TesterProto.Empty> processBig(
        proto.TesterProto.BigData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessBigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PROCESS_SMALL = 0;
  private static final int METHODID_PROCESS_MEDIUM = 1;
  private static final int METHODID_PROCESS_BIG = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TesterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TesterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PROCESS_SMALL:
          serviceImpl.processSmall((proto.TesterProto.SmallData) request,
              (io.grpc.stub.StreamObserver<proto.TesterProto.Empty>) responseObserver);
          break;
        case METHODID_PROCESS_MEDIUM:
          serviceImpl.processMedium((proto.TesterProto.MediumData) request,
              (io.grpc.stub.StreamObserver<proto.TesterProto.Empty>) responseObserver);
          break;
        case METHODID_PROCESS_BIG:
          serviceImpl.processBig((proto.TesterProto.BigData) request,
              (io.grpc.stub.StreamObserver<proto.TesterProto.Empty>) responseObserver);
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

  private static abstract class TesterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TesterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.TesterProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Tester");
    }
  }

  private static final class TesterFileDescriptorSupplier
      extends TesterBaseDescriptorSupplier {
    TesterFileDescriptorSupplier() {}
  }

  private static final class TesterMethodDescriptorSupplier
      extends TesterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TesterMethodDescriptorSupplier(String methodName) {
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
      synchronized (TesterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TesterFileDescriptorSupplier())
              .addMethod(getProcessSmallMethod())
              .addMethod(getProcessMediumMethod())
              .addMethod(getProcessBigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
