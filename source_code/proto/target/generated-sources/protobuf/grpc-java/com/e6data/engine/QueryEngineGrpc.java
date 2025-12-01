package com.e6data.engine;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: query_engine.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QueryEngineGrpc {

  private QueryEngineGrpc() {}

  public static final java.lang.String SERVICE_NAME = "queryengine.QueryEngine";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.e6data.engine.TaskRequest,
      com.e6data.engine.TaskResponse> getProcessTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProcessTask",
      requestType = com.e6data.engine.TaskRequest.class,
      responseType = com.e6data.engine.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.e6data.engine.TaskRequest,
      com.e6data.engine.TaskResponse> getProcessTaskMethod() {
    io.grpc.MethodDescriptor<com.e6data.engine.TaskRequest, com.e6data.engine.TaskResponse> getProcessTaskMethod;
    if ((getProcessTaskMethod = QueryEngineGrpc.getProcessTaskMethod) == null) {
      synchronized (QueryEngineGrpc.class) {
        if ((getProcessTaskMethod = QueryEngineGrpc.getProcessTaskMethod) == null) {
          QueryEngineGrpc.getProcessTaskMethod = getProcessTaskMethod =
              io.grpc.MethodDescriptor.<com.e6data.engine.TaskRequest, com.e6data.engine.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProcessTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.e6data.engine.TaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.e6data.engine.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryEngineMethodDescriptorSupplier("ProcessTask"))
              .build();
        }
      }
    }
    return getProcessTaskMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryEngineStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryEngineStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryEngineStub>() {
        @java.lang.Override
        public QueryEngineStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryEngineStub(channel, callOptions);
        }
      };
    return QueryEngineStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryEngineBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryEngineBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryEngineBlockingStub>() {
        @java.lang.Override
        public QueryEngineBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryEngineBlockingStub(channel, callOptions);
        }
      };
    return QueryEngineBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QueryEngineFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryEngineFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryEngineFutureStub>() {
        @java.lang.Override
        public QueryEngineFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryEngineFutureStub(channel, callOptions);
        }
      };
    return QueryEngineFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void processTask(com.e6data.engine.TaskRequest request,
        io.grpc.stub.StreamObserver<com.e6data.engine.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProcessTaskMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service QueryEngine.
   */
  public static abstract class QueryEngineImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return QueryEngineGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service QueryEngine.
   */
  public static final class QueryEngineStub
      extends io.grpc.stub.AbstractAsyncStub<QueryEngineStub> {
    private QueryEngineStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryEngineStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryEngineStub(channel, callOptions);
    }

    /**
     */
    public void processTask(com.e6data.engine.TaskRequest request,
        io.grpc.stub.StreamObserver<com.e6data.engine.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProcessTaskMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service QueryEngine.
   */
  public static final class QueryEngineBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<QueryEngineBlockingStub> {
    private QueryEngineBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryEngineBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryEngineBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.e6data.engine.TaskResponse processTask(com.e6data.engine.TaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProcessTaskMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service QueryEngine.
   */
  public static final class QueryEngineFutureStub
      extends io.grpc.stub.AbstractFutureStub<QueryEngineFutureStub> {
    private QueryEngineFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryEngineFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryEngineFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.e6data.engine.TaskResponse> processTask(
        com.e6data.engine.TaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProcessTaskMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PROCESS_TASK = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PROCESS_TASK:
          serviceImpl.processTask((com.e6data.engine.TaskRequest) request,
              (io.grpc.stub.StreamObserver<com.e6data.engine.TaskResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getProcessTaskMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.e6data.engine.TaskRequest,
              com.e6data.engine.TaskResponse>(
                service, METHODID_PROCESS_TASK)))
        .build();
  }

  private static abstract class QueryEngineBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QueryEngineBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.e6data.engine.QueryEngineProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QueryEngine");
    }
  }

  private static final class QueryEngineFileDescriptorSupplier
      extends QueryEngineBaseDescriptorSupplier {
    QueryEngineFileDescriptorSupplier() {}
  }

  private static final class QueryEngineMethodDescriptorSupplier
      extends QueryEngineBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    QueryEngineMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (QueryEngineGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QueryEngineFileDescriptorSupplier())
              .addMethod(getProcessTaskMethod())
              .build();
        }
      }
    }
    return result;
  }
}
