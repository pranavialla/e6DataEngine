package com.e6data.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileProcessor implements FileProcessor {
    private final FileReader fileReader;

    public CsvFileProcessor(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public List<StudentRecord> process(String filePath) throws IOException {
        List<String> lines = fileReader.readLines(filePath);
        return parseLines(lines);
    }

    private List<StudentRecord> parseLines(List<String> lines) {
        List<StudentRecord> records = new ArrayList<>();

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
}





