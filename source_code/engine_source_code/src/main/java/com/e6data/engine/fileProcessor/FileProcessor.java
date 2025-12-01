package com.e6data.engine.fileProcessor;

import java.io.IOException;
import java.util.List;

import com.e6data.engine.StudentRecord;

public interface FileProcessor {
    List<StudentRecord> process(String filePath) throws IOException;
}

