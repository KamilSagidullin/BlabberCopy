package com.example.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;
@Getter
@ToString
public class CreatePostApplicationEvent extends ApplicationEvent {
    private long postId;
    private int authorId;
    private String authorUsername;

    public CreatePostApplicationEvent(Object source, long postId, int authorId, String authorUsername) {
        super(source);
        this.postId = postId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
    }
}
