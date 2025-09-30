package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostKafkaEvent  {
    private Long postId;
    private int authorId;
    private String username;
}

