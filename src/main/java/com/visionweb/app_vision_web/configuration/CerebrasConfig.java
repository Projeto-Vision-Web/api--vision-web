package com.visionweb.app_vision_web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CerebrasConfig     {

    @Value("${ceresbras.api-key}")
    private String apiKey;

    @Bean
    public WebClient cerebrasWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.cerebras.ai/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
