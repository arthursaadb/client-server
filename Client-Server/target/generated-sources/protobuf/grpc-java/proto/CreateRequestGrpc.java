package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: protocol.proto")
public final class CreateRequestGrpc {

  private CreateRequestGrpc() {}

  public static final String SERVICE_NAME = "proto.CreateRequest";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSolicitationMethod()} instead. 
  public static final io.grpc.MethodDescriptor<proto.SolicitationRequest,
      proto.SolicitationReply> METHOD_SOLICITATION = getSolicitationMethodHelper();

  private static volatile io.grpc.MethodDescriptor<proto.SolicitationRequest,
      proto.SolicitationReply> getSolicitationMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<proto.SolicitationRequest,
      proto.SolicitationReply> getSolicitationMethod() {
    return getSolicitationMethodHelper();
  }

  private static io.grpc.MethodDescriptor<proto.SolicitationRequest,
      proto.SolicitationReply> getSolicitationMethodHelper() {
    io.grpc.MethodDescriptor<proto.SolicitationRequest, proto.SolicitationReply> getSolicitationMethod;
    if ((getSolicitationMethod = CreateRequestGrpc.getSolicitationMethod) == null) {
      synchronized (CreateRequestGrpc.class) {
        if ((getSolicitationMethod = CreateRequestGrpc.getSolicitationMethod) == null) {
          CreateRequestGrpc.getSolicitationMethod = getSolicitationMethod = 
              io.grpc.MethodDescriptor.<proto.SolicitationRequest, proto.SolicitationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "proto.CreateRequest", "Solicitation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.SolicitationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.SolicitationReply.getDefaultInstance()))
                  .setSchemaDescriptor(new CreateRequestMethodDescriptorSupplier("Solicitation"))
                  .build();
          }
        }
     }
     return getSolicitationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CreateRequestStub newStub(io.grpc.Channel channel) {
    return new CreateRequestStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CreateRequestBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CreateRequestBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CreateRequestFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CreateRequestFutureStub(channel);
  }

  /**
   */
  public static abstract class CreateRequestImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void solicitation(proto.SolicitationRequest request,
        io.grpc.stub.StreamObserver<proto.SolicitationReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSolicitationMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSolicitationMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.SolicitationRequest,
                proto.SolicitationReply>(
                  this, METHODID_SOLICITATION)))
          .build();
    }
  }

  /**
   */
  public static final class CreateRequestStub extends io.grpc.stub.AbstractStub<CreateRequestStub> {
    private CreateRequestStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CreateRequestStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreateRequestStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CreateRequestStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void solicitation(proto.SolicitationRequest request,
        io.grpc.stub.StreamObserver<proto.SolicitationReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSolicitationMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CreateRequestBlockingStub extends io.grpc.stub.AbstractStub<CreateRequestBlockingStub> {
    private CreateRequestBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CreateRequestBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreateRequestBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CreateRequestBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public java.util.Iterator<proto.SolicitationReply> solicitation(
        proto.SolicitationRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSolicitationMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CreateRequestFutureStub extends io.grpc.stub.AbstractStub<CreateRequestFutureStub> {
    private CreateRequestFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CreateRequestFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreateRequestFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CreateRequestFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SOLICITATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CreateRequestImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CreateRequestImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SOLICITATION:
          serviceImpl.solicitation((proto.SolicitationRequest) request,
              (io.grpc.stub.StreamObserver<proto.SolicitationReply>) responseObserver);
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

  private static abstract class CreateRequestBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CreateRequestBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.Protocol.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CreateRequest");
    }
  }

  private static final class CreateRequestFileDescriptorSupplier
      extends CreateRequestBaseDescriptorSupplier {
    CreateRequestFileDescriptorSupplier() {}
  }

  private static final class CreateRequestMethodDescriptorSupplier
      extends CreateRequestBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CreateRequestMethodDescriptorSupplier(String methodName) {
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
      synchronized (CreateRequestGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CreateRequestFileDescriptorSupplier())
              .addMethod(getSolicitationMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
