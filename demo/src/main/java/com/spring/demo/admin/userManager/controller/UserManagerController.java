package com.spring.demo.admin.userManager.controller;

import com.spring.demo.admin.userManager.dto.UserManagerDTO;
import com.spring.demo.admin.userManager.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @GetMapping("api/users")
    public ResponseEntity<List<UserManagerDTO>> getAllUsers(){
        List<UserManagerDTO> users = userManagerService.getUserState();

        return ResponseEntity.ok(users);
    }
}
