package com.e6data.engine;

import java.util.List;

import io.grpc.stub.StreamObserver;

/**
 * gRPC Service implementation
 * SRP: Handles gRPC protocol translation only
 */
class QueryEngineService extends QueryEngineGrpc.QueryEngineImplBase {
    private final TaskProcessor taskProcessor;

    public QueryEngineService(TaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
    }

    @Override
    public void processTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
        try {
            List<StudentRecord> records = taskProcessor.process(request.getFilePathsList());

            TaskResponse.Builder responseBuilder = TaskResponse.newBuilder();
            for (StudentRecord record : records) {
                responseBuilder.addRecords(
                        new StudentRecord( record.studentId, record.batchYear, record.universityRanking, record.batchRanking) 
                );
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}

