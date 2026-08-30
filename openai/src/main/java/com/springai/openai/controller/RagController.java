package com.springai.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    @Value("classpath:/promptTemplates/systemRandomDataTemplate.st")
    Resource randomDataTemplate;

    public RagController(@Qualifier("chatMemoryChatClient") ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/information")
    public ResponseEntity<String> getInformation(@RequestHeader("username") String username, @RequestParam("message") String message) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3)
                .similarityThreshold(0.5)       // probability of matching data
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        String extractedDocuments = documents
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        String content = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec
                        .text(randomDataTemplate)
                        .param("documents", extractedDocuments))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(content);

    }
}
