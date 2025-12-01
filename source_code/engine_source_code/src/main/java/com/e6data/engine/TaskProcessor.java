package com.e6data.engine;

import java.util.List;

/**
 * Processes task requests
 * SRP: Orchestrates file processing and sorting
 */
public interface TaskProcessor {
    List<StudentRecord> process(List<String> filePaths) throws Exception;
}

