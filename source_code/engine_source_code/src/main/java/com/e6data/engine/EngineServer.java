package com.e6data.engine;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

import com.e6data.engine.fileProcessingOrchestrator.ParallelFileProcessingOrchestrator;
import com.e6data.engine.fileProcessor.CsvFileProcessor;
import com.e6data.engine.fileProcessor.FileProcessor;
import com.e6data.engine.fileReaders.CsvFileReader;
import com.e6data.engine.recordSorter.MergeSortRecordSorter;
import com.e6data.engine.recordSorter.RecordSorter;
import com.e6data.engine.taskProcessor.DefaultTaskProcessor;
import com.e6data.engine.taskProcessor.TaskProcessor;

/**
 * Engine Server - Handles gRPC server lifecycle
 * Follows Single Responsibility Principle
 */
public class EngineServer {
    private final int port;
    private final QueryEngineService queryEngineService;
    private Server server;

    public EngineServer(int port, QueryEngineService queryEngineService) {
        this.port = port;
        this.queryEngineService = queryEngineService;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(queryEngineService)
                .build()
                .start();

        System.out.println("Engine started on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down engine on port " + port);
            EngineServer.this.stop();
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java EngineServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        // Dependency injection
        FileProcessor fileProcessor = new CsvFileProcessor(new CsvFileReader());
        ParallelFileProcessingOrchestrator orchestrator = new ParallelFileProcessingOrchestrator(fileProcessor);
        RecordSorter sorter = new MergeSortRecordSorter();
        TaskProcessor taskProcessor = new DefaultTaskProcessor(orchestrator, sorter);
        QueryEngineService service = new QueryEngineService(taskProcessor);

        EngineServer server = new EngineServer(port, service);
        server.start();
        server.blockUntilShutdown();
    }
}

