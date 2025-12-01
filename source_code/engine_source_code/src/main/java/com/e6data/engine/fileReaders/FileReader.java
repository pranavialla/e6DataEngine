package com.e6data.engine.fileReaders;

import java.io.IOException;
import java.util.List;

/**
 * Reads file contents
 * SRP: Single responsibility of file I/O
 * DIP: Abstract file reading
 */
public interface FileReader {
    List<String> readLines(String filePath) throws IOException;
}