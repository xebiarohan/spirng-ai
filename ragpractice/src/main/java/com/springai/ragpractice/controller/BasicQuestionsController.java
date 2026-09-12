package com.springai.ragpractice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
public class BasicQuestionsController {

    public final ChatClient chatClient;

    public BasicQuestionsController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/query")
    public String queryHandler(@RequestParam String message, @RequestHeader String id) {
        return chatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, id))
                .call()
                .content();
    }
}
