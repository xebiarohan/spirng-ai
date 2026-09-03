package com.springai.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ReactiveController {

    public final ChatClient chatClient;

    public ReactiveController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/data")
    public Flux<String> getInformation(@RequestParam("message") String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content()
                .map(String::toUpperCase)
                .filter(x -> x.length() > 2)
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("Completed"))
                .doOnError((error) -> System.out.println(error.getMessage()));

    }
}
