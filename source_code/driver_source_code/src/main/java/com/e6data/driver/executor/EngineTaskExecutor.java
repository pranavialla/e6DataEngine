package com.e6data.driver.executor;

import java.util.List;

import com.e6data.driver.Record;

/**
 * Executes tasks on a single engine
 * DIP: Depends on abstraction (EngineChannelFactory)
 */
public interface EngineTaskExecutor {
    List<Record> executeTask(int port, List<String> files);
}
