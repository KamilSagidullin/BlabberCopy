package com.example.blabbercopy.repository;

import com.example.blabbercopy.entity.Subscription;
import com.example.blabbercopy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
   @Query("select s.follower from Subscription s where s.followee.id = :followeeId")
    List<User> getFollowersByFolloweeId(Integer followeeId);

    boolean existsByFollowerIdAndFolloweeId(Integer followerId, Integer followeeId);

    void deleteByFollowerIdAndFolloweeId(Integer followerId, Integer followeeId);
    int deleteAllByFolloweeIdOrFollowerId(Integer followeeId, Integer followerId);
}
