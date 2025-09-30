package com.example.blabbercopy.service;

import com.example.blabbercopy.entity.Subscription;
import com.example.blabbercopy.entity.SubscriptionType;
import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.event.SubscriptionChangeApplicationEvent;
import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @CacheEvict(value = "userSubscriptions",key = "#followeeId")
    public void subscribe(int followeeId,int followerId){
        User followee = userService.findById(followeeId);
        User follower = userService.findById(followerId);

        if (followee == null || follower == null) throw new BlabberException("Invalid followee or follower");

        if (!subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId,followeeId)) {
            Subscription subscription = new Subscription();
            subscription.setFollowee(followee);
            subscription.setFollower(follower);

            subscriptionRepository.save(subscription);
            applicationEventPublisher.publishEvent(new SubscriptionChangeApplicationEvent(this,followeeId,followerId, SubscriptionType.SUBSCRIBE));

        }
    }
    @Transactional
    @CacheEvict(value = "userSubscriptions",key = "#followeeId")
    public void unsubscribe(int followeeId,int followerId){
        User followee = userService.findById(followeeId);
        User follower = userService.findById(followerId);

        if (followee == null || follower == null) throw new BlabberException("Invalid followee or follower");
        if (!subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId,followeeId)) throw new BlabberException("Subscription does not exists");

        subscriptionRepository.deleteByFollowerIdAndFolloweeId(followerId,followeeId);
        log.info("Deleted subscription ");

        applicationEventPublisher.publishEvent(new SubscriptionChangeApplicationEvent(this,followeeId,followerId,SubscriptionType.UNSUBSCRIBE));
    }
    @Cacheable(value = "userSubscriptions",key = "#followeeId")
    public List<User> getAllFollowers(int followeeId){
        log.info("Get followers by followee {}", followeeId);
        return subscriptionRepository.getFollowersByFolloweeId(followeeId);
    }
}
