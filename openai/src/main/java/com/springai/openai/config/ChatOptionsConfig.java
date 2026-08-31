package com.springai.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class ChatOptionsConfig {


    @Bean("chatOptionsExample")
    @Primary
    public ChatClient getChatClient(ChatClient.Builder builder) {
        OpenAiChatOptions.Builder options = OpenAiChatOptions.builder()
                .model("gpt-5.4-mini")
                .topP(0.75)
                .stopSequences(List.of("DONE"))
                .maxCompletionTokens(100);

        return builder
                .defaultOptions(options)
                .build();
    }
}
