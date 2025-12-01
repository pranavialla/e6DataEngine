package com.e6data.driver.engineChanelFactory;

import io.grpc.ManagedChannel;

/**
 * Factory for creating gRPC channels
 * DIP: Abstract the channel creation
 */
public interface EngineChannelFactory {
    ManagedChannel createChannel(String host, int port);
}

