package com.spring.demo.security.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
}
