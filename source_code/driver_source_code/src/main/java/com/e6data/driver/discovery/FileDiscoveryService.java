package com.e6data.driver.discovery;

import java.io.IOException;
import java.util.List;

/**
 * Service to discover CSV files
 * SRP: Single responsibility of file discovery
 */
public interface FileDiscoveryService {
    List<String> discoverFiles() throws IOException;
}
