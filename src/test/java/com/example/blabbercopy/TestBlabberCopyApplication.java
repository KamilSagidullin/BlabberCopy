package com.example.blabbercopy;

import org.springframework.boot.SpringApplication;

public class TestBlabberCopyApplication {

    public static void main(String[] args) {
        SpringApplication.from(BlabberCopyApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
