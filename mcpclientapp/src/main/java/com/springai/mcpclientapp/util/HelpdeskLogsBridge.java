package com.springai.mcpclientapp.util;

import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.annotation.McpLogging;
import org.springframework.stereotype.Component;

@Component
public class HelpdeskLogsBridge {
    private static final Logger log = LoggerFactory.getLogger(HelpdeskLogsBridge.class);

    @McpLogging(clients = "helpdesk")
    public void onServerLog(McpSchema.LoggingLevel level,
                            String source,
                            String message) {
        log.info("Received log from server - Level: {}, Source: {}, Message: {}", level, source, message);
    }
}
