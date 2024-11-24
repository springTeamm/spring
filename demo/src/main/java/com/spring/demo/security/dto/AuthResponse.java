package com.spring.demo.security.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String userRole;

    // 두 개의 String을 받는 생성자 추가
    public AuthResponse(String token, String userRole) {
        this.token = token;
        this.userRole = userRole;
    }
}
