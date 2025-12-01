package com.e6data.engine.taskProcessor;

import java.util.List;

import com.e6data.engine.StudentRecord;
import com.e6data.engine.fileProcessingOrchestrator.FileProcessingOrchestrator;
import com.e6data.engine.recordSorter.RecordSorter;

public class DefaultTaskProcessor implements TaskProcessor {
    private final FileProcessingOrchestrator orchestrator;
    private final RecordSorter sorter;

    public DefaultTaskProcessor(FileProcessingOrchestrator orchestrator, RecordSorter sorter) {
        this.orchestrator = orchestrator;
        this.sorter = sorter;
    }

    @Override
    public List<StudentRecord> process(List<String> filePaths) throws Exception {
        List<StudentRecord> records = orchestrator.processFiles(filePaths);
        return sorter.sort(records);
    }
}

