package com.spring.demo.security.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String userRole;
}
