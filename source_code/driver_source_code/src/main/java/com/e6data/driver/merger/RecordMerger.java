package com.e6data.driver.merger;

import java.util.List;

import com.e6data.driver.Record;

/**
 * Merges sorted records
 * OCP: Can add different merge strategies
 */
public interface RecordMerger {
    List<Record> merge(List<Record> records);
}

