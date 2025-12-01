package com.e6data.driver.executor;

import java.util.ArrayList;
import java.util.List;

import com.e6data.driver.Record;
import com.e6data.driver.engineChanelFactory.EngineChannelFactory;
import com.e6data.engine.QueryEngineGrpc;
import com.e6data.engine.TaskRequest;
import com.e6data.engine.TaskResponse;

import io.grpc.ManagedChannel;

public class GrpcEngineTaskExecutor implements EngineTaskExecutor {
    private final EngineChannelFactory channelFactory;

    public GrpcEngineTaskExecutor(EngineChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
    }

    @Override
    public List<Record> executeTask(int port, List<String> files) {
        ManagedChannel channel = channelFactory.createChannel("localhost", port);

        try {
            QueryEngineGrpc.QueryEngineBlockingStub stub = QueryEngineGrpc.newBlockingStub(channel);

            TaskRequest request = TaskRequest.newBuilder()
                    .addAllFilePaths(files)
                    .build();

            System.out.println("Sending " + files.size() + " files to engine on port " + port);

            TaskResponse response = stub.processTask(request);

            List<Record> records = new ArrayList<>();
            for (com.e6data.engine.StudentRecord sr : response.getRecordsList()) {
                records.add(new Record(sr.getStudentId(), sr.getBatchYear(),
                        sr.getUniversityRanking(), sr.getBatchRanking()));
            }

            System.out.println("Received " + records.size() + " records from port " + port);
            return records;

        } catch (Exception e) {
            System.err.println("Error communicating with engine on port " + port + ": " + e.getMessage());
            return new ArrayList<>();
        } finally {
            channel.shutdown();
        }
    }
}

