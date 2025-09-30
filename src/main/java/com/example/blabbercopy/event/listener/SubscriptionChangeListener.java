package com.example.event.listener;

import com.example.client.SubscriptionClient;
import com.example.entity.SubscriptionType;
import com.example.event.SubscriptionChangeApplicationEvent;
import com.example.exception.BlabberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionChangeListener {

    private final SubscriptionClient subscriptionClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
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
