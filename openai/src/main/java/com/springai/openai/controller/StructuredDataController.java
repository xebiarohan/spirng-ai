package com.springai.openai.controller;

import com.springai.openai.model.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StructuredDataController {

    public final ChatClient chatClient;

    public StructuredDataController(ChatClient.Builder builder){
        chatClient = builder.build();
    }

    @GetMapping("/record")
    public ResponseEntity<Data> getRecord(@RequestParam String message) {
        Data entity = chatClient
                .prompt()
//                .system("""
//                        Always return the response as a list. if the output cannot pe put in a list then
//                        return an empty list
//
//                        example ['a', 'b', 'c'], [1,2,3]
//                        """)
                .user(message)
                .call()
                .entity(Data.class);

        return org.springframework.http.ResponseEntity.ok(entity);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getListData(@RequestParam String message) {
        List<String> entity = chatClient
                .prompt()
//                .system("""
//                        Always return the response as a list. if the output cannot pe put in a list then
//                        return an empty list
//
//                        example ['a', 'b', 'c'], [1,2,3]
//                        """)
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {});

        return org.springframework.http.ResponseEntity.ok(entity);
    }

}
