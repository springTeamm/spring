package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_num")
    private Long id;

    @Column(name = "User_Id", nullable = false, unique = true)
    private String username;

    @Column(name = "User_pwd", nullable = false)
    private String password;

    @Column(name = "User_email", nullable = false, unique = true)
    private String email;

    @Column(name = "User_rights", nullable = false)
    private String role; // 호스트와 일반 회원 구분

    @Column(name = "User_name")
    private String name;

    @Column(name = "User_nickName")
    private String nickName;

    @Column(name = "User_phone")
    private String phone;
}