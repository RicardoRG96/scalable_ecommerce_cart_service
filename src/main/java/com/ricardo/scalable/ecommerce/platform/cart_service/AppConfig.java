package com.ricardo.scalable.ecommerce.platform.cart_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    WebClient.Builder webClient() {
        return WebClient.builder();
    }

}
