package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User")
@Getter @Setter
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_num")
    private Long id;

    @Column(name = "User_Id", nullable = false)
    private String userId;

    @Column(name = "User_pwd", nullable = false)
    private String password;

    @Column(name = "User_email")
    private String email;

    @Column(name = "User_rights")
    private String userRights;

    @Column(name = "User_name")
    private String name;

    @Column(name = "User_nickName")
    private String nickName;

    @Column(name = "User_phone")
    private String phone;

    @OneToOne(mappedBy = "user")
    private HostEntity host;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRights));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}