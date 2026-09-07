package com.springai.openai.config;

import com.springai.openai.advisors.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBuilderCustomizerConfig {

    @Bean
    public ChatClientBuilderCustomizer loggerAdvisor() {
        return builder -> builder
                .defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean
    @ConditionalOnProperty(name = "audit.token-usage.enabled", havingValue = "true")
    public ChatClientBuilderCustomizer auditAdvisor() {
        return builder -> builder
                .defaultAdvisors(new TokenUsageAdvisor());
    }
}
