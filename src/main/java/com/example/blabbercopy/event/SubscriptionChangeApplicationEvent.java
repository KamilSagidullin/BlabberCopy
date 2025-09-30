package com.example.blabbercopy.event;

import com.example.blabbercopy.entity.SubscriptionType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
@Getter
@ToString
public class SubscriptionChangeApplicationEvent extends ApplicationEvent {
    private int followeeId;
    private int followerId;
    private final SubscriptionType subscriptionType;

    public SubscriptionChangeApplicationEvent(Object source, int foloweeId, int followerId, SubscriptionType subscriptionType) {
        super(source);
        this.followeeId = foloweeId;
        this.followerId = followerId;
        this.subscriptionType = subscriptionType;
    }
}
