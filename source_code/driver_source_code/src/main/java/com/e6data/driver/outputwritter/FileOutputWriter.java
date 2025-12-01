package com.e6data.driver.outputwritter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.e6data.driver.Record;

public class FileOutputWriter implements OutputWriter {
    private final String outputFilePath;

    public FileOutputWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void write(List<Record> records) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Record record : records) {
                writer.write(record.studentId + "\n");
            }
        }
        System.out.println("Output written to " + outputFilePath);
    }
}

