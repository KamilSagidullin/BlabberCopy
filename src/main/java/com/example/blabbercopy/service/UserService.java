package com.example.blabbercopy.service;

import com.example.blabbercopy.entity.SubscriptionType;
import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.event.SubscriptionChangeApplicationEvent;
import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.repository.SubscriptionRepository;
import com.example.blabbercopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Cacheable(value = "userById",key = "#userId")
    public User findById(int userId){
        log.info("Finding user by id {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new BlabberException("User not found"));

    }
    public User create(User user){
        log.info("Creating user {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Transactional
    public void deleteById(int userId){
        log.info("Deleting user by id {}", userId);
        int countOfDelete = subscriptionRepository.deleteAllByFolloweeIdOrFollowerId(userId,userId);

        log.info("Delete subscriptions {}", countOfDelete);
        applicationEventPublisher.publishEvent(new SubscriptionChangeApplicationEvent(this,userId,userId, SubscriptionType.REMOVE));

        userRepository.deleteById(userId);
    }
}
