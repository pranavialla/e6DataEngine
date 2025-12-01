package com.e6data.driver.coordinator;

import java.util.List;

import com.e6data.driver.Record;

/**
 * Coordinates execution across multiple engines
 * SRP: Manages parallel execution
 */
public interface EngineCoordinator {
    List<Record> processInParallel(List<List<String>> fileBatches) throws Exception;
}
