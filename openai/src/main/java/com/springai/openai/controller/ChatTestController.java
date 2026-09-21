package com.springai.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ChatTestController {

    private final ChatClient chatClient;

    @Value("classpath:/promptTemplates/hrPolicy.st")
    Resource hrPolicyTemplate;

    public ChatTestController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("test-chat")
    public String getSolution(@RequestParam("message") String message) {
        return this.chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/test-prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message) {
        return chatClient
                .prompt().system(hrPolicyTemplate)
                .user(message)
                .call().content();
    }



}
