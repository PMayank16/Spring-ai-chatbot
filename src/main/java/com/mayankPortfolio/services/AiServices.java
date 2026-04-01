package com.mayankPortfolio.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AiServices {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/system.st")
    private Resource systemMessage;

    @Value("classpath:/prompts/user.st")
    private Resource userMessage;

    public AiServices(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Mono<String> askai(String query) {
        return chatClient
                .prompt()
                .system(s -> s.text(systemMessage))
                .user(u -> u.text(userMessage).param("input", query))
                .stream()
                .content()
                .collectList()
                .map(list -> String.join("", list))
                .map(this::cleanResponse)
                .filter(s -> !s.isBlank());
    }

    private String cleanResponse(String text) {
        return text
                .replaceAll("(?s)<think>.*?</think>", "") // remove think
                .replaceAll("\\s+", " ")                 // fix spacing
                .trim();
    }
}