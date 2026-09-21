package com.springai.mcpclientapp.controller;

import com.springai.mcpclientapp.util.ToolUtil;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class McpClientController {

    private final ChatClient chatClient;
    private final List<McpSyncClient> mcpClients;

    public McpClientController(ChatClient.Builder chatClientBuilder,
                               ToolCallbackProvider toolCallbackProvider, List<McpSyncClient> mcpClients) {
        this.chatClient = chatClientBuilder.defaultTools(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        this.mcpClients = mcpClients;
    }

    @GetMapping("/chat")
    public String chat(@RequestHeader(value = "username",required = false) String username,
                       @RequestParam("message") String message) {
        return chatClient.prompt().user(message+ " My username is " + username)
                .call().content();
    }

    @GetMapping("/summarize-tickets")
    public String summarizeTickets(@RequestHeader("username") String username) {
        ToolCallback[] toolCallbacks = ToolUtil.selectToolsFor(mcpClients, "helpdesk-mcp-server", null);
        return chatClient.prompt()
                .system("""
                        You orchestrate the 'summarizeTickets' tool. The tool already returns a complete,
                        customer-ready summary that was generated for this exact request. Return that tool
                        output to the user EXACTLY as-is: do not rewrite, reformat, shorten, expand,
                        rephrase, or add any commentary of your own. Your reply must be the verbatim tool
                        response and nothing else.
                        """)
                .user("Summarize all of my support tickets. My username is " + username)
                .tools(toolCallbacks)
                .toolContext(Map.of("progressToken", UUID.randomUUID().toString()))
                .call().content();
    }
}
