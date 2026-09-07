package com.springai.openai.config;

import com.springai.openai.advisors.TokenUsageAdvisor;
import com.springai.openai.tools.Timetools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient timeChatClient(ChatClient.Builder builder, ChatMemory chatMemory, Timetools timetools) {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        TokenUsageAdvisor tokenUsageAdvisor = new TokenUsageAdvisor();
        return builder
                .defaultTools(timetools)
                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor, tokenUsageAdvisor))
                .defaultSystem(systemPromptTemplate)
                .build();
    }

    /**
     * Use when we want to send the error message to client instead to the LLM. By default, LLM creates this bean with false value
     * if the value is false, the error message will be sent to the LLM and it will return a readable message to the client
     * if the value is true then it will send the error directly to the client
     * @return
     */
    @Bean
    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }

}
