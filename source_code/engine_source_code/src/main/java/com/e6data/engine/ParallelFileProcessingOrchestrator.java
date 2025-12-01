package com.e6data.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.grpc.netty.shaded.io.netty.util.concurrent.Future;

class ParallelFileProcessingOrchestrator implements FileProcessingOrchestrator {
    private final FileProcessor fileProcessor;

    public ParallelFileProcessingOrchestrator(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public List<StudentRecord> processFiles(List<String> filePaths) throws Exception {
        List<StudentRecord> allRecords = new ArrayList<>();

        int numThreads = Math.min(Runtime.getRuntime().availableProcessors(), filePaths.size());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<StudentRecord>>> futures = new ArrayList<>();

        for (String filePath : filePaths) {
            futures.add((Future<List<StudentRecord>>) executor.submit(() -> fileProcessor.process(filePath)));
        }

        // Collect results
        for (Future<List<StudentRecord>> future : futures) {
            try {
                allRecords.addAll(future.get());
            } catch (Exception e) {
                System.err.println("Error processing file: " + e.getMessage());
            }
        }

        executor.shutdown();
        return allRecords;
    }
}

