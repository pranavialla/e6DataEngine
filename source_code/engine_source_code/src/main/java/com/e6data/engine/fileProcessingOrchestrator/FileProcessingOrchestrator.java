package com.e6data.engine.fileProcessingOrchestrator;

import java.util.List;

import com.e6data.engine.StudentRecord;

/**
 * Orchestrates file processing
 * OCP: Can be extended with different orchestration strategies
 */
public interface FileProcessingOrchestrator {
    List<StudentRecord> processFiles(List<String> filePaths) throws Exception;
}
