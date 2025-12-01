package com.e6data.driver.fileDistribution;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinDistributionStrategy implements FileDistributionStrategy {
    private final int numEngines;

    public RoundRobinDistributionStrategy(int numEngines) {
        this.numEngines = numEngines;
    }

    @Override
    public List<List<String>> distribute(List<String> files) {
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < numEngines; i++) {
            batches.add(new ArrayList<>());
        }

        for (int i = 0; i < files.size(); i++) {
            batches.get(i % numEngines).add(files.get(i));
        }

        return batches;
    }
}

