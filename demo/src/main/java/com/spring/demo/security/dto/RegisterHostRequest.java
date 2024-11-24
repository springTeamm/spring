package com.spring.demo.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterHostRequest {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;

    // 호스트 추가 정보
    private String businessType;
    private String companyName;
    private String businessRegistrationNumber;
    private String businessAddress;
    private String communicationSalesNumber;
}
