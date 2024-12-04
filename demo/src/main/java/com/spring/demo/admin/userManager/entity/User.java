package com.spring.demo.admin.userManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue
    @Column(name="User_num")
    private Integer userNum;

    @Column(name="User_id")
    private String userId;

    @Column(name = "User_pwd")
    private String userPwd;

    @Column(name = "User_email")
    private String userEmail;

    @Column(name = "User_rights")
    private String userRights;

    @Column(name = "User_name")
    private String userName;

    @Column(name = "User_nickname")
    private String userNickname;

    @Column(name = "User_phone")
    private String userPhone;

    @Column(name = "User_makingTIme")
    private LocalDateTime userMakingTime;

    public User(Integer userNum, String userId, String userPwd, String userEmail, String userRights, String userName, String userNickname, String userPhone, LocalDateTime userMakingTime) {
        this.userNum = userNum;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userEmail = userEmail;
        this.userRights = userRights;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPhone = userPhone;
        this.userMakingTime = userMakingTime;
    }
}
