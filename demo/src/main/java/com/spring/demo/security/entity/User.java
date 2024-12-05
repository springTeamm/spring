package com.spring.demo.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_num")
    private Integer userNum; // 사용자 고유 번호 (PK)

    @Column(name = "User_id")
    private String userId; // 사용자 ID (로그인 시 사용)

    @Column(name = "User_password")
    private String userPassword; // 사용자 비밀번호

    @Column(name = "Comfirm_userpassword")
    private String confirmUserPassword;

    @Column(name = "User_email")
    private String userEmail; // 사용자 이메일

    @Column(name = "User_name")
    private String userName; // 사용자 이름




    @Column(name = "User_phone")
    private String userPhone; // 사용자 전화번호


    @Column(name = "User_rights")
    private String userRights; // 사용자 권한

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Hosts hosts; // 사용자는 호스트와 1:1 관계 (호스트가 있으면 관련된 정보도 함께 관리)

    public User(Integer userNum, String userName, String userId, String userEmail, String userPhone, String userPassword, String confirmUserPassword) {
        this.userNum = userNum;
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.confirmUserPassword = confirmUserPassword;
    }

    // isEmpty() 메소드 구현
    public boolean isHostsEmpty() {
        return hosts == null || hosts.getUser().isHostsEmpty();
    }
}
