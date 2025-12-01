package com.e6data.engine;

import java.util.List;

/**
 * Executes tasks on a single engine
 * DIP: Depends on abstraction (EngineChannelFactory)
 */
interface EngineTaskExecutor {
    List<Record> executeTask(int port, List<String> files);
}
