package com.e6data.engine.taskProcessor;

import java.util.List;

import com.e6data.engine.StudentRecord;

/**
 * Processes task requests
 * SRP: Orchestrates file processing and sorting
 */
public interface TaskProcessor {
    List<StudentRecord> process(List<String> filePaths) throws Exception;
}

