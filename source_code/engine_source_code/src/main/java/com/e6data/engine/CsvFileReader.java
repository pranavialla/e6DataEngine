package com.e6data.engine;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.file.Files;
import java.nio.file.Path;


class CsvFileReader implements FileReader {
    @Override
    public List<String> readLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}