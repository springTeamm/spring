package com.spring.demo.security.controller;

import com.spring.demo.security.dto.HostDTO;
import com.spring.demo.security.dto.UserDTO;
import com.spring.demo.security.service.HostService;
import com.spring.demo.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final HostService hostService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 일반 사용자 회원가입 페이지
    @GetMapping("/signup/user")
    public String userSignupPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup-user";
    }

    // 일반 사용자 회원가입 처리
    @PostMapping("/signup/user")
    public String userSignup(@Valid @ModelAttribute UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "signup-user";
        }

        try {
            // 회원가입 서비스 호출
            userService.registerUser(userDTO);
            return "redirect:/login?signup=success";
        } catch (RuntimeException e) {
            // 중복 아이디, 이메일 등의 오류 처리
            model.addAttribute("errorMessage", e.getMessage());
            return "signup-user";
        }
    }

    // 호스트 회원가입 페이지
    @GetMapping("/signup/host")
    public String hostSignupPage(Model model) {
        model.addAttribute("hostDTO", new HostDTO());
        return "signup-host";
    }

    // 호스트 회원가입 처리
    @PostMapping("/signup/host")
    public String hostSignup(@Valid @ModelAttribute HostDTO hostDTO,
                             BindingResult bindingResult,
                             Model model) {
        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            return "signup-host";
        }

        try {
            // 호스트 회원가입 서비스 호출
            hostService.registerHost(hostDTO);
            return "redirect:/login?signup=success";
        } catch (RuntimeException e) {
            // 중복 아이디, 사업자 번호 등의 오류 처리
            model.addAttribute("errorMessage", e.getMessage());
            return "signup-host";
        }
    }
}
