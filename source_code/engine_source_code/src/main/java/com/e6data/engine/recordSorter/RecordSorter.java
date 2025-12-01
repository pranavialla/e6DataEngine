package com.e6data.engine.recordSorter;

import java.util.List;

import com.e6data.engine.StudentRecord;

public interface RecordSorter {
    List<StudentRecord> sort(List<StudentRecord> records);
}
