package com.example.blabbercopy.web.controller;
import com.example.blabbercopy.security.AppUserDetails;
import com.example.blabbercopy.service.SubscriptionService;
import com.example.blabbercopy.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> subscribe(@RequestParam Long followeeId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        var followerId = ((AppUserDetails)userDetails).getId();
        subscriptionService.subscribe(followerId, followeeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unsubscribe")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> unsubscribe(@RequestParam Long followeeId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        var followerId = ((AppUserDetails)userDetails).getId();
        subscriptionService.unsubscribe(followerId, followeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@RequestParam Long followeeId) {
        List<UserDto> followers = subscriptionService.getFollowers(followeeId)
                .stream()
                .map(it -> new UserDto(it.getId(), it.getUsername()))
                .toList();
        return ResponseEntity.ok(followers);
    }

}
