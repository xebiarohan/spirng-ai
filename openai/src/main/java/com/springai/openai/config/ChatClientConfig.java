package com.springai.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(
                        """
                            You are an HR assistant, your job is to help employees with questions\s
                            related to HR policies.If the user ask anything outside that, kindly\s
                            inform them this is out of your scope.
                        """
                )
                .defaultUser("How can you help me ?")
                .build();
    }
}
