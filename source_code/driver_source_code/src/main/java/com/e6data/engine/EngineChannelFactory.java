package com.e6data.engine;

import io.grpc.ManagedChannel;

/**
 * Factory for creating gRPC channels
 * DIP: Abstract the channel creation
 */
interface EngineChannelFactory {
    ManagedChannel createChannel(String host, int port);
}

