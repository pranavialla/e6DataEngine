package com.e6data.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

class ParallelEngineCoordinator implements EngineCoordinator {
    private final List<Integer> enginePorts;
    private final EngineTaskExecutor taskExecutor;

    public ParallelEngineCoordinator(List<Integer> enginePorts, EngineTaskExecutor taskExecutor) {
        this.enginePorts = enginePorts;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public List<Record> processInParallel(List<List<String>> fileBatches) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(enginePorts.size());
        List<Future<List<Record>>> futures = new ArrayList<>();

        for (int i = 0; i < enginePorts.size() && i < fileBatches.size(); i++) {
            final int port = enginePorts.get(i);
            final List<String> files = fileBatches.get(i);

            if (!files.isEmpty()) {
                futures.add(executor.submit(() -> taskExecutor.executeTask(port, files)));
            }
        }

        List<Record> allRecords = new ArrayList<>();
        for (Future<List<Record>> future : futures) {
            try {
                allRecords.addAll(future.get());
            } catch (Exception e) {
                System.err.println("Error getting results: " + e.getMessage());
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return allRecords;
    }
}

