package com.example.blabbercopy.security;

import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.repository.UserRepository;
import com.example.blabbercopy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new BlabberException("User with username " + username + " not found"));
    }
}
