package com.spring.demo.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
}
