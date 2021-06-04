package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: Office.proto")
public final class OfficeGrpc {

  private OfficeGrpc() {}

  public static final String SERVICE_NAME = "office.Office";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.OfficeProto.CaseRequestData,
      proto.OfficeProto.RegisterCaseResult> getCaseRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "caseRequest",
      requestType = proto.OfficeProto.CaseRequestData.class,
      responseType = proto.OfficeProto.RegisterCaseResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<proto.OfficeProto.CaseRequestData,
      proto.OfficeProto.RegisterCaseResult> getCaseRequestMethod() {
    io.grpc.MethodDescriptor<proto.OfficeProto.CaseRequestData, proto.OfficeProto.RegisterCaseResult> getCaseRequestMethod;
    if ((getCaseRequestMethod = OfficeGrpc.getCaseRequestMethod) == null) {
      synchronized (OfficeGrpc.class) {
        if ((getCaseRequestMethod = OfficeGrpc.getCaseRequestMethod) == null) {
          OfficeGrpc.getCaseRequestMethod = getCaseRequestMethod =
              io.grpc.MethodDescriptor.<proto.OfficeProto.CaseRequestData, proto.OfficeProto.RegisterCaseResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "caseRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.OfficeProto.CaseRequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.OfficeProto.RegisterCaseResult.getDefaultInstance()))
              .setSchemaDescriptor(new OfficeMethodDescriptorSupplier("caseRequest"))
              .build();
        }
      }
    }
    return getCaseRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.OfficeProto.Client,
      proto.OfficeProto.CaseResultSequence> getHelloRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "helloRequest",
      requestType = proto.OfficeProto.Client.class,
      responseType = proto.OfficeProto.CaseResultSequence.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.OfficeProto.Client,
      proto.OfficeProto.CaseResultSequence> getHelloRequestMethod() {
    io.grpc.MethodDescriptor<proto.OfficeProto.Client, proto.OfficeProto.CaseResultSequence> getHelloRequestMethod;
    if ((getHelloRequestMethod = OfficeGrpc.getHelloRequestMethod) == null) {
      synchronized (OfficeGrpc.class) {
        if ((getHelloRequestMethod = OfficeGrpc.getHelloRequestMethod) == null) {
          OfficeGrpc.getHelloRequestMethod = getHelloRequestMethod =
              io.grpc.MethodDescriptor.<proto.OfficeProto.Client, proto.OfficeProto.CaseResultSequence>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "helloRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.OfficeProto.Client.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.OfficeProto.CaseResultSequence.getDefaultInstance()))
              .setSchemaDescriptor(new OfficeMethodDescriptorSupplier("helloRequest"))
              .build();
        }
      }
    }
    return getHelloRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OfficeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OfficeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OfficeStub>() {
        @java.lang.Override
        public OfficeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OfficeStub(channel, callOptions);
        }
      };
    return OfficeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OfficeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OfficeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OfficeBlockingStub>() {
        @java.lang.Override
        public OfficeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OfficeBlockingStub(channel, callOptions);
        }
      };
    return OfficeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OfficeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OfficeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OfficeFutureStub>() {
        @java.lang.Override
        public OfficeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OfficeFutureStub(channel, callOptions);
        }
      };
    return OfficeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class OfficeImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<proto.OfficeProto.CaseRequestData> caseRequest(
        io.grpc.stub.StreamObserver<proto.OfficeProto.RegisterCaseResult> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCaseRequestMethod(), responseObserver);
    }

    /**
     */
    public void helloRequest(proto.OfficeProto.Client request,
        io.grpc.stub.StreamObserver<proto.OfficeProto.CaseResultSequence> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelloRequestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCaseRequestMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                proto.OfficeProto.CaseRequestData,
                proto.OfficeProto.RegisterCaseResult>(
                  this, METHODID_CASE_REQUEST)))
          .addMethod(
            getHelloRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                proto.OfficeProto.Client,
                proto.OfficeProto.CaseResultSequence>(
                  this, METHODID_HELLO_REQUEST)))
          .build();
    }
  }

  /**
   */
  public static final class OfficeStub extends io.grpc.stub.AbstractAsyncStub<OfficeStub> {
    private OfficeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OfficeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OfficeStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<proto.OfficeProto.CaseRequestData> caseRequest(
        io.grpc.stub.StreamObserver<proto.OfficeProto.RegisterCaseResult> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCaseRequestMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void helloRequest(proto.OfficeProto.Client request,
        io.grpc.stub.StreamObserver<proto.OfficeProto.CaseResultSequence> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelloRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OfficeBlockingStub extends io.grpc.stub.AbstractBlockingStub<OfficeBlockingStub> {
    private OfficeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OfficeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OfficeBlockingStub(channel, callOptions);
    }

    /**
     */
    public proto.OfficeProto.CaseResultSequence helloRequest(proto.OfficeProto.Client request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelloRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OfficeFutureStub extends io.grpc.stub.AbstractFutureStub<OfficeFutureStub> {
    private OfficeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OfficeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OfficeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.OfficeProto.CaseResultSequence> helloRequest(
        proto.OfficeProto.Client request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelloRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO_REQUEST = 0;
  private static final int METHODID_CASE_REQUEST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OfficeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OfficeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO_REQUEST:
          serviceImpl.helloRequest((proto.OfficeProto.Client) request,
              (io.grpc.stub.StreamObserver<proto.OfficeProto.CaseResultSequence>) responseObserver);
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
        case METHODID_CASE_REQUEST:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.caseRequest(
              (io.grpc.stub.StreamObserver<proto.OfficeProto.RegisterCaseResult>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class OfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OfficeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.OfficeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Office");
    }
  }

  private static final class OfficeFileDescriptorSupplier
      extends OfficeBaseDescriptorSupplier {
    OfficeFileDescriptorSupplier() {}
  }

  private static final class OfficeMethodDescriptorSupplier
      extends OfficeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OfficeMethodDescriptorSupplier(String methodName) {
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
      synchronized (OfficeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OfficeFileDescriptorSupplier())
              .addMethod(getCaseRequestMethod())
              .addMethod(getHelloRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
