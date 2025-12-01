package com.e6data.engine;

import java.util.List;

/**
 * Merges sorted records
 * OCP: Can add different merge strategies
 */
interface RecordMerger {
    List<Record> merge(List<Record> records);
}

