package com.example.blabbercopy.web.dto;

import com.example.blabbercopy.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private RoleType role;
}
