package com.springai.ragpractice.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        OpenAiChatOptions.Builder optionsBuilder = OpenAiChatOptions.builder()
                .topP(0.9)
                .temperature(0.8);

        return builder
                .defaultAdvisors(new SimpleLoggerAdvisor(), memoryAdvisor)
                .defaultSystem("""
                        Reply in maximum of 25 words
                        """)
                .defaultOptions(optionsBuilder)
                .build();
    }
}
