package com.e6data.engine;

import java.util.List;

/**
 * Coordinates execution across multiple engines
 * SRP: Manages parallel execution
 */
interface EngineCoordinator {
    List<Record> processInParallel(List<List<String>> fileBatches) throws Exception;
}
