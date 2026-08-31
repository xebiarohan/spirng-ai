package com.springai.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatOptionsController {


    public final ChatClient chatClient;

    public ChatOptionsController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInformation(@RequestParam String message) {
        String content = chatClient
                .prompt()
                .system("""
                        Give small maximum 10 words answer.
                        """)
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(content);
    }
}
