package com.springai.mcpclientapp.controller;

import com.springai.mcpclientapp.util.ToolUtil;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class McpClientWithFilteredToolsController {

    private final ChatClient chatClient;

    private final List<McpSyncClient> mcpClients;

    public McpClientWithFilteredToolsController(ChatClient.Builder chatClientBuilder,
                               List<McpSyncClient> mcpClients) {
        this.mcpClients = mcpClients;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/info")
    public String chat(@RequestHeader(value = "username",required = false) String username,
                       @RequestParam("message") String message) {
        ToolCallback[] toolCallbacks = ToolUtil.selectToolsFor(mcpClients, "helpdesk-mcp-server", "getTicketStatus");
        return chatClient.prompt()
                .user(message+ " My username is " + username)
                .tools(toolCallbacks)
                .call().content();
    }
}
