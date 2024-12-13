package com.spring.demo.security.controller;

import com.spring.demo.security.dto.HostDTO;
import com.spring.demo.security.dto.HostSignupDTO;
import com.spring.demo.security.dto.UserDTO;
import com.spring.demo.security.service.HostService;
import com.spring.demo.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final HostService hostService;

    // 일반 사용자 회원가입 처리
    @PostMapping("/signup/user")
    public ResponseEntity<?> userSignup(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            // 회원가입 서비스 호출
            userService.registerUser(userDTO);
        } catch (RuntimeException e) {
            // 중복 아이디, 이메일 등의 오류 처리
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 호스트 회원가입 처리
    @PostMapping("/signup/host")
    public ResponseEntity<HostDTO> hostSignup(@Valid @RequestBody HostSignupDTO hostDTO, BindingResult bindingResult) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            // 호스트 회원가입 서비스 호출
            hostService.registerHost(hostDTO);
        } catch (RuntimeException e) {
            // 중복 아이디, 사업자 번호 등의 오류 처리
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
