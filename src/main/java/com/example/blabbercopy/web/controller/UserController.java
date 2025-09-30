package com.example.web.controller;

import com.example.blabbercopy.web.dto.CreateUserRequest;
import com.example.blabbercopy.web.dto.UserDTO;
import com.example.entity.RoleType;
import com.example.entity.User;
import com.example.service.UserService;
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername()));

    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User(createUserRequest.getUsername(), createUserRequest.getPassword(), RoleType.ROLE_USER);
        User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(createdUser.getId(), createdUser.getUsername()));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable int userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
