package com.e6data.engine;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class EngineServer {
    private final int port;
    private Server server;

    public EngineServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new QueryEngineService())
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

    static class QueryEngineService extends QueryEngineGrpc.QueryEngineImplBase {
        @Override
        public void processTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
            try {
                List<StudentRecord> records = processFiles(request.getFilePathsList());
                
                TaskResponse.Builder responseBuilder = TaskResponse.newBuilder();
                for (StudentRecord record : records) {
                    responseBuilder.addRecords(
                        com.e6data.engine.StudentRecord.newBuilder()
                            .setStudentId(record.studentId)
                            .setBatchYear(record.batchYear)
                            .setUniversityRanking(record.universityRanking)
                            .setBatchRanking(record.batchRanking)
                            .build()
                    );
                }
                
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
                
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }

        private List<StudentRecord> processFiles(List<String> filePaths) throws IOException {
            List<StudentRecord> allRecords = new ArrayList<>();
            
            // Use thread pool for parallel file processing
            int numThreads = Math.min(Runtime.getRuntime().availableProcessors(), filePaths.size());
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            List<Future<List<StudentRecord>>> futures = new ArrayList<>();
            
            for (String filePath : filePaths) {
                futures.add(executor.submit(() -> readAndParseFile(filePath)));
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
            
            // Custom merge sort implementation (NOT using Collections.sort!)
            return customMergeSort(allRecords);
        }

        private List<StudentRecord> readAndParseFile(String filePath) throws IOException {
            List<StudentRecord> records = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    records.add(new StudentRecord(
                        parts[0].trim(),
                        Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim()),
                        Integer.parseInt(parts[3].trim())
                    ));
                }
            }
            
            return records;
        }

        // Custom Merge Sort Implementation (Required by assignment!)
        private List<StudentRecord> customMergeSort(List<StudentRecord> list) {
            if (list.size() <= 1) {
                return list;
            }
            
            int mid = list.size() / 2;
            List<StudentRecord> left = customMergeSort(new ArrayList<>(list.subList(0, mid)));
            List<StudentRecord> right = customMergeSort(new ArrayList<>(list.subList(mid, list.size())));
            
            return merge(left, right);
        }

        private List<StudentRecord> merge(List<StudentRecord> left, List<StudentRecord> right) {
            List<StudentRecord> result = new ArrayList<>();
            int i = 0, j = 0;
            
            while (i < left.size() && j < right.size()) {
                if (left.get(i).compareTo(right.get(j)) <= 0) {
                    result.add(left.get(i++));
                } else {
                    result.add(right.get(j++));
                }
            }
            
            while (i < left.size()) result.add(left.get(i++));
            while (j < right.size()) result.add(right.get(j++));
            
            return result;
        }
    }

    // Internal StudentRecord class for Engine processing
    static class StudentRecord implements Comparable<StudentRecord> {
        String studentId;
        int batchYear;
        int universityRanking;
        int batchRanking;

        public StudentRecord(String studentId, int batchYear, int universityRanking, int batchRanking) {
            this.studentId = studentId;
            this.batchYear = batchYear;
            this.universityRanking = universityRanking;
            this.batchRanking = batchRanking;
        }

        @Override
        public int compareTo(StudentRecord other) {
            // Sort by batch_year, then university_ranking, then batch_ranking
            int yearComp = Integer.compare(this.batchYear, other.batchYear);
            if (yearComp != 0) return yearComp;
            
            int uniComp = Integer.compare(this.universityRanking, other.universityRanking);
            if (uniComp != 0) return uniComp;
            
            return Integer.compare(this.batchRanking, other.batchRanking);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java EngineServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        EngineServer server = new EngineServer(port);
        server.start();
        server.blockUntilShutdown();
    }
}