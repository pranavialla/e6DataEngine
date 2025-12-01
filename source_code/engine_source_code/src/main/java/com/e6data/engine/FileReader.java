package com.e6data.engine;

import java.io.IOException;
import java.util.List;

/**
 * Reads file contents
 * SRP: Single responsibility of file I/O
 * DIP: Abstract file reading
 */
interface FileReader {
    List<String> readLines(String filePath) throws IOException;
}