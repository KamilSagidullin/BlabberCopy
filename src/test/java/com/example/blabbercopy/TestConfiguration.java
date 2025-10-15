package com.example.blabbercopy;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
public class TestConfiguration {
    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
