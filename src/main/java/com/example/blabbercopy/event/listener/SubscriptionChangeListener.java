package com.example.blabbercopy.event.listener;

import com.example.blabbercopy.client.SubscriptionClient;
import com.example.blabbercopy.entity.SubscriptionType;
import com.example.blabbercopy.event.SubscriptionChangeApplicationEvent;
import com.example.blabbercopy.exception.BlabberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionChangeListener {

    private final SubscriptionClient subscriptionClient;

    @EventListener
    public void onEvent(SubscriptionChangeApplicationEvent event) {
        log.info("Received subscription change event {}", event);
        switch (event.getSubscriptionType()){
            case SubscriptionType.SUBSCRIBE -> {
                subscriptionClient.subscribe(event.getFolloweeId(), event.getFollowerId());
            }
            case SubscriptionType.UNSUBSCRIBE -> {
                subscriptionClient.unsubscribe(event.getFolloweeId(),event.getFollowerId());
            }
            case SubscriptionType.REMOVE -> {
                subscriptionClient.deleteSubscription(event.getFolloweeId());
            }
            default -> {
                throw new BlabberException("Subscription type not found: " + event.getSubscriptionType()); 
            }
        }
    }
}
