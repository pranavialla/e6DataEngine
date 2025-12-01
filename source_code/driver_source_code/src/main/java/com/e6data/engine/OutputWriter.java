package com.e6data.engine;

import java.io.IOException;
import java.util.List;

/**
 * Writes output records
 * SRP: Single responsibility of writing output
 */
public interface OutputWriter {
    void write(List<Record> records) throws IOException;
}

