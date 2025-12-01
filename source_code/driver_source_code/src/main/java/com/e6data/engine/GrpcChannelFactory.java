package com.e6data.engine;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

class GrpcChannelFactory implements EngineChannelFactory {
    @Override
    public ManagedChannel createChannel(String host, int port) {
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }
}

