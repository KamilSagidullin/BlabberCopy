package com.example.blabbercopy.repository;

import com.example.blabbercopy.entity.Subscription;
import com.example.blabbercopy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.follower FROM Subscription s WHERE s.followee.id = :followeeId")
    List<User> findFollowersByFolloweeId(@Param("followeeId") Long followeeId);

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    int deleteAllByFolloweeIdOrFollowerId(Long followeeId, Long followerId);

}

