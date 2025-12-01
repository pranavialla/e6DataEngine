package com.e6data.engine;

import java.io.IOException;
import java.util.List;

/**
 * Service to discover CSV files
 * SRP: Single responsibility of file discovery
 */
interface FileDiscoveryService {
    List<String> discoverFiles() throws IOException;
}
