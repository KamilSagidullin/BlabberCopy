package com.example.web.controller;

import com.example.entity.User;
import com.example.security.AppUserDetails;
import com.example.service.SubscriptionService;
import com.example.blabbercopy.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> subscribe(@RequestParam int followeeId, @AuthenticationPrincipal UserDetails userDetails){
        var followerId = ((AppUserDetails)userDetails).getId();
        subscriptionService.subscribe(followeeId,followerId);
        return ResponseEntity.noContent().build();

    }
    @PostMapping("/unsubscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> unsubscribe(@RequestParam int followeeId, @AuthenticationPrincipal UserDetails userDetails){
        var followerId = ((AppUserDetails)userDetails).getId();
        subscriptionService.unsubscribe(followeeId,followerId);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/followers")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<UserDTO>> getAllFollowers(int followeeId){
         List<UserDTO> followers = subscriptionService.getAllFollowers(followeeId).stream().map(user ->
            new UserDTO(user.getId(),user.getUsername())
        ).toList();
        return ResponseEntity.ok(followers);
    }
}

