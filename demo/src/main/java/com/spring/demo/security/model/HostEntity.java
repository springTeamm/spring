package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hosts")
@Getter
@Setter
public class HostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Host_num")
    private Long id;

    @OneToOne
    @JoinColumn(name = "User_num")
    private UserEntity user;

    @OneToOne(mappedBy = "host", cascade = CascadeType.ALL)
    private HostInfoEntity hostInfo;
}
