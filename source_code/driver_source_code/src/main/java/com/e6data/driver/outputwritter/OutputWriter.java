package com.e6data.driver.outputwritter;

import java.io.IOException;
import java.util.List;

import com.e6data.driver.Record;

/**
 * Writes output records
 * SRP: Single responsibility of writing output
 */
public interface OutputWriter {
    void write(List<Record> records) throws IOException;
}

