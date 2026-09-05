package com.springai.openai.config;

import com.springai.openai.advisors.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springai.openai.tools.Timetools;

import java.util.List;

@Configuration
public class TimeChatClientConfig {


    @Bean("timeChatClient")
    public ChatClient timeChatClient(ChatClient.Builder builder, ChatMemory chatMemory, Timetools timetools) {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        TokenUsageAdvisor tokenUsageAdvisor = new TokenUsageAdvisor();
        return builder
                .defaultTools(timetools)
                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor, tokenUsageAdvisor))
                .build();
    }

}
