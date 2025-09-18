package com.example.blabbercopy.event.listener;

import com.example.blabbercopy.client.SubscriptionClient;
import com.example.blabbercopy.event.SubscriptionChangeApplicationEvent;
import com.example.blabbercopy.exception.BlabberException;
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
            case SUBSCRIBE -> {
                subscriptionClient.subscribe(event.getFolloweeId(), event.getFollowerId());
            }
            case UNSUBSCRIBE -> {
                subscriptionClient.unsubscribe(event.getFolloweeId(),event.getFollowerId());
            }
            case REMOVE -> {
                subscriptionClient.deleteSubscription(event.getFolloweeId());
            }
            default -> {
                throw new BlabberException("Subscription type not found: " + event.getSubscriptionType()); 
            }
        }
    }
}
