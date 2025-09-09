package com.example.blabbercopy.service;

import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(int id){
        log.info("Finding user by id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new BlabberException("User not found"));

    }
    public User create(User user){
        log.info("Creating user {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
