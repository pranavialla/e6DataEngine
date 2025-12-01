package com.e6data.engine;

import java.util.List;

/**
 * Orchestrates file processing
 * OCP: Can be extended with different orchestration strategies
 */
interface FileProcessingOrchestrator {
    List<StudentRecord> processFiles(List<String> filePaths) throws Exception;
}
