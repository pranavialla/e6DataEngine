package com.e6data.driver.fileDistribution;

import java.util.List;

/**
 * Strategy for distributing files across engines
 * OCP: Open for extension (can add new distribution strategies)
 */
public interface FileDistributionStrategy {
    List<List<String>> distribute(List<String> files);
}

