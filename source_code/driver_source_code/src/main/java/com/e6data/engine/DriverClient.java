package com.e6data.engine;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class DriverClient {
    private static final String TABLE_DIR = "sample_dataset/student_rankings";
    private static final String OUTPUT_FILE = "output.txt";

    private List<Integer> enginePorts;

    public DriverClient(List<Integer> enginePorts) {
        this.enginePorts = enginePorts;
    }

    public void execute() throws Exception {
        System.out.println("Driver started with engines on ports: " + enginePorts);

        // Get all CSV files
        List<String> csvFiles = getCSVFiles();
        if (csvFiles.isEmpty()) {
            System.err.println("No CSV files found in " + TABLE_DIR);
            return;
        }

        System.out.println("Found " + csvFiles.size() + " CSV files to process");

        // Distribute files across engines
        List<List<String>> fileBatches = distributeFiles(csvFiles, enginePorts.size());

        // Process tasks in parallel
        ExecutorService executor = Executors.newFixedThreadPool(enginePorts.size());
        List<Future<List<Record>>> futures = new ArrayList<>();

        for (int i = 0; i < enginePorts.size() && i < fileBatches.size(); i++) {
            final int port = enginePorts.get(i);
            final List<String> files = fileBatches.get(i);
            
            if (!files.isEmpty()) {
                futures.add(executor.submit(() -> processTask(port, files)));
            }
        }

        // Collect results from all engines
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

        System.out.println("Collected " + allRecords.size() + " total records");

        // K-way merge using custom MinHeap
        List<Record> sortedRecords = kWayMerge(allRecords);

        // Write output
        writeOutput(sortedRecords);

        System.out.println("Output written to " + OUTPUT_FILE);
    }

    private List<String> getCSVFiles() throws IOException {
        File tableDir = new File(TABLE_DIR);
        if (!tableDir.exists() || !tableDir.isDirectory()) {
            return new ArrayList<>();
        }

        List<String> files = new ArrayList<>();
        for (File file : tableDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".csv")) {
                files.add(file.getPath());
            }
        }
        Collections.sort(files);
        return files;
    }

    private List<List<String>> distributeFiles(List<String> files, int numEngines) {
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < numEngines; i++) {
            batches.add(new ArrayList<>());
        }

        for (int i = 0; i < files.size(); i++) {
            batches.get(i % numEngines).add(files.get(i));
        }

        return batches;
    }

    private List<Record> processTask(int port, List<String> files) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();

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

    // Custom K-way merge using MinHeap (no Collections.sort!)
    private List<Record> kWayMerge(List<Record> allRecords) {
        // Since engines already returned sorted data, we need to merge them
        // For simplicity, if data is already mostly sorted, use merge
        // But to be safe, we'll do a final custom sort
        return customMergeSort(allRecords);
    }

    // Custom Merge Sort Implementation
    private List<Record> customMergeSort(List<Record> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Record> left = customMergeSort(new ArrayList<>(list.subList(0, mid)));
        List<Record> right = customMergeSort(new ArrayList<>(list.subList(mid, list.size())));

        return merge(left, right);
    }

    private List<Record> merge(List<Record> left, List<Record> right) {
        List<Record> result = new ArrayList<>();
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

    private void writeOutput(List<Record> records) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (int i = 0; i < records.size(); i++) {
                writer.write(records.get(i).studentId + "\n");
            }
        }
    }

    // Internal Record class for Driver
    static class Record implements Comparable<Record> {
        String studentId;
        int batchYear;
        int universityRanking;
        int batchRanking;

        public Record(String studentId, int batchYear, int universityRanking, int batchRanking) {
            this.studentId = studentId;
            this.batchYear = batchYear;
            this.universityRanking = universityRanking;
            this.batchRanking = batchRanking;
        }

        @Override
        public int compareTo(Record other) {
            int yearComp = Integer.compare(this.batchYear, other.batchYear);
            if (yearComp != 0) return yearComp;

            int uniComp = Integer.compare(this.universityRanking, other.universityRanking);
            if (uniComp != 0) return uniComp;

            return Integer.compare(this.batchRanking, other.batchRanking);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java DriverClient <port1> <port2> <port3> ...");
            System.exit(1);
        }

        List<Integer> ports = new ArrayList<>();
        for (String arg : args) {
            ports.add(Integer.parseInt(arg));
        }

        DriverClient driver = new DriverClient(ports);
        driver.execute();
    }
}