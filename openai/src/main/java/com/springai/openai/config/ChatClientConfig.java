package com.springai.openai.config;

import com.springai.openai.advisors.TokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        var chatOptions = OpenAiChatOptions.builder()
                .model("gpt-5.4-mini")
                .temperature(0.8)
                .maxCompletionTokens(200);

        return builder
                .defaultOptions(chatOptions)
                .defaultSystem(
                        """
                            You are an HR assistant, your job is to help employees with questions\s
                            related to HR policies.If the user ask anything outside that, kindly\s
                            inform them this is out of your scope.
                        """
                )
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(new TokenUsageAdvisor())
                .defaultUser("How can you help me ?")
                .build();
    }
}
