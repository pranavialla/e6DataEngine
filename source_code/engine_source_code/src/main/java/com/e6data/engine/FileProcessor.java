package com.e6data.engine;

import java.io.IOException;
import java.util.List;

public interface FileProcessor {
    List<StudentRecord> process(String filePath) throws IOException;
}

