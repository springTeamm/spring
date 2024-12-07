package com.spring.demo.security.controller;

import com.spring.demo.security.dto.HostDTO;
import com.spring.demo.security.dto.UserDTO;
import com.spring.demo.security.service.HostService;
import com.spring.demo.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final HostService hostService;


    // 일반 사용자 회원가입 페이지
    @GetMapping("/signup/user")
    public String userSignupPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup-user";
    }

    // 일반 사용자 회원가입 처리
    @PostMapping("/signup/user")
    public ResponseEntity<?> userSignup(@Valid @ModelAttribute UserDTO userDTO,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있을 경우 400 응답을 보냄
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            // 회원가입 서비스 호출
            userService.registerUser(userDTO);
            // 성공 시 200 OK와 함께 성공 메시지 반환
            return ResponseEntity.ok().body("회원가입 성공");
        } catch (RuntimeException e) {
            // 예외 발생 시 500 오류와 함께 에러 메시지 반환
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // 호스트 회원가입 페이지
    @PostMapping("/signup/host")
    public ResponseEntity<?> hostSignup(@Valid @RequestBody HostDTO hostDTO, // @RequestBody로 JSON 매핑
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 응답
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            // 호스트 회원가입 서비스 호출
            hostService.registerHost(hostDTO);
            return ResponseEntity.ok("호스트 회원가입 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/login/user")
    public ResponseEntity<?> userLogin(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.validateUser(userDTO.getUserId(), userDTO.getUserPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("사용자 로그인 성공");
        } else {
            return ResponseEntity.status(401).body("사용자 로그인 실패: 잘못된 아이디 또는 비밀번호");
        }
    }

    @PostMapping("/login/host")
    public ResponseEntity<?> hostLogin(@RequestBody HostDTO hostDTO) {
        boolean isAuthenticated = hostService.validateHost(hostDTO.getUserId(), hostDTO.getUserPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("호스트 로그인 성공");
        } else {
            return ResponseEntity.status(401).body("호스트 로그인 실패: 잘못된 아이디 또는 비밀번호");
        }
    }

}
