package com.spring.demo.security.controller;

import com.spring.demo.security.dto.AuthRequest;
import com.spring.demo.security.dto.AuthResponse;
import com.spring.demo.security.dto.RegisterHostRequest;
import com.spring.demo.security.dto.RegisterUserRequest;
import com.spring.demo.security.model.User;
import com.spring.demo.security.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        boolean exists = authService.registerUser(request);
        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email or User ID already in use");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/host")
    public ResponseEntity<?> registerHost(@RequestBody RegisterHostRequest request) {
        boolean exists = authService.registerHost(request);
        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email or User ID already in use");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody AuthRequest request) {
        User user = authService.authenticateUser(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // 세션에 사용자 정보 저장
        session.setAttribute("user", user);
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("Logout successful");
    }
}