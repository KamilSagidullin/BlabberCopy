package com.example.event.listener;

import com.example.event.CreatePostApplicationEvent;
import com.example.event.CreatePostKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePostListener {
    @Value("${app.kafka.topic}")
    private String topic;

    private KafkaTemplate<String,CreatePostKafkaEvent> postKafkaTemplate;

    @EventListener
    public void onEvent(CreatePostApplicationEvent applicationEvent){
        log.info("Get event for creating post");
        postKafkaTemplate.send(topic,new CreatePostKafkaEvent(applicationEvent.getPostId(), applicationEvent.getAuthorId(),applicationEvent.getAuthorUsername()));
    }
}
