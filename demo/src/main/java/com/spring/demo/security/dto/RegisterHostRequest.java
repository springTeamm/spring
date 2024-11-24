package com.spring.demo.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterHostRequest {
    // 기본 정보
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    private String nickName;
    @NotBlank
    private String phone;

    // Host 정보
    @NotBlank
    private String businessAddress;
    @NotBlank
    private String businessItem;
    @NotBlank
    private String registrationNumber;
    @NotBlank
    private String businessType;
    @NotBlank
    private String companyName;
    private String corporateName;
    private String corporateNumber;
    @NotNull
    private LocalDateTime openDate;
    @NotBlank
    private String taxType;
}