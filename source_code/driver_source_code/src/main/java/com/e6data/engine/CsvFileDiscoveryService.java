package com.e6data.engine;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

class CsvFileDiscoveryService implements FileDiscoveryService {
    private final String directoryPath;

    public CsvFileDiscoveryService(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public List<String> discoverFiles() throws IOException {
        File tableDir = new File(directoryPath);
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
}

