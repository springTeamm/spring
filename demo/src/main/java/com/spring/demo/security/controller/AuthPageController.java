package com.spring.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }

}
