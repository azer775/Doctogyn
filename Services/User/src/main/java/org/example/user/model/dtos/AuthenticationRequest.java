package org.example.user.model.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String pwd;  // Password
    private String email;  // Email (username)
}
