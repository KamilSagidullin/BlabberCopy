package com.example.blabbercopy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlabberCopyApplication {

    public static void main(String[] args) {
        System.out.println("Default TimeZone: " + java.util.TimeZone.getDefault().getID());

        SpringApplication.run(BlabberCopyApplication.class, args);
    }

}
