package com.spring.demo.security.dto;

import lombok.Data;

@Data
public class RegisterHostRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    // 호스트 정보 (수정 필요)
    private String businessType;
    private String companyName;
    private String businessNumber;
    private String businessAddress;
    private String businessCategory;
    private String businessCondition;
    private String businessSector;
    private String onlineMarketingNumber;
    private String representativeName;
    private String bankName;
    private String accountNumber;
    private String studioAddress;
    private String studioPhoneNumber;
}