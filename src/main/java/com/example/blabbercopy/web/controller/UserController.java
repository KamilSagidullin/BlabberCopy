package com.example.blabbercopy.web.controller;

import com.example.blabbercopy.web.dto.CreateUserRequest;
import com.example.blabbercopy.web.dto.UserDto;
import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername()));

    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User(createUserRequest.getUsername(), createUserRequest.getPassword(), createUserRequest.getRole());
        User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDto(createdUser.getId(), createdUser.getUsername()));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
