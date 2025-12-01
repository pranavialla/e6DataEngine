package com.e6data.driver;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

import com.e6data.driver.coordinator.EngineCoordinator;
import com.e6data.driver.coordinator.ParallelEngineCoordinator;
import com.e6data.driver.discovery.CsvFileDiscoveryService;
import com.e6data.driver.discovery.FileDiscoveryService;
import com.e6data.driver.engineChanelFactory.EngineChannelFactory;
import com.e6data.driver.engineChanelFactory.GrpcChannelFactory;
import com.e6data.driver.executor.EngineTaskExecutor;
import com.e6data.driver.executor.GrpcEngineTaskExecutor;
import com.e6data.driver.fileDistribution.FileDistributionStrategy;
import com.e6data.driver.fileDistribution.RoundRobinDistributionStrategy;
import com.e6data.driver.merger.MergeSortRecordMerger;
import com.e6data.driver.merger.RecordMerger;
import com.e6data.driver.outputwritter.FileOutputWriter;
import com.e6data.driver.outputwritter.OutputWriter;


/**
 * Main Driver Client - Orchestrates the distributed query execution
 * Follows Single Responsibility Principle
 */
public class DriverClient {
    private final FileDiscoveryService fileDiscoveryService;
    private final FileDistributionStrategy distributionStrategy;
    private final EngineCoordinator engineCoordinator;
    private final RecordMerger recordMerger;
    private final OutputWriter outputWriter;

    public DriverClient(
            FileDiscoveryService fileDiscoveryService,
            FileDistributionStrategy distributionStrategy,
            EngineCoordinator engineCoordinator,
            RecordMerger recordMerger,
            OutputWriter outputWriter) {
        this.fileDiscoveryService = fileDiscoveryService;
        this.distributionStrategy = distributionStrategy;
        this.engineCoordinator = engineCoordinator;
        this.recordMerger = recordMerger;
        this.outputWriter = outputWriter;
    }

    public void execute() throws Exception {
        System.out.println("Driver started");

        // Discover files
        List<String> csvFiles = fileDiscoveryService.discoverFiles();
        if (csvFiles.isEmpty()) {
            System.err.println("No CSV files found");
            return;
        }

        System.out.println("Found " + csvFiles.size() + " CSV files to process");

        // Distribute files across engines
        List<List<String>> fileBatches = distributionStrategy.distribute(csvFiles);

        // Process tasks in parallel
        List<Record> allRecords = engineCoordinator.processInParallel(fileBatches);

        System.out.println("Collected " + allRecords.size() + " total records");

        // Merge sorted records
        List<Record> sortedRecords = recordMerger.merge(allRecords);

        // Write output
        outputWriter.write(sortedRecords);

        System.out.println("Processing complete");
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

        // Dependency injection
        DriverClient driver = createDriverClient(ports);
        driver.execute();
    }

    private static DriverClient createDriverClient(List<Integer> ports) {
        String tableDir = "sample_dataset/student_rankings";
        String outputFile = "output.txt";

        FileDiscoveryService fileDiscovery = new CsvFileDiscoveryService(tableDir);
        FileDistributionStrategy distribution = new RoundRobinDistributionStrategy(ports.size());
        EngineChannelFactory channelFactory = new GrpcChannelFactory();
        EngineTaskExecutor taskExecutor = new GrpcEngineTaskExecutor(channelFactory);
        EngineCoordinator coordinator = new ParallelEngineCoordinator(ports, taskExecutor);
        RecordMerger merger = new MergeSortRecordMerger();
        OutputWriter writer = new FileOutputWriter(outputFile);

        return new DriverClient(fileDiscovery, distribution, coordinator, merger, writer);
    }
}

