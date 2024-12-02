package com.spring.demo.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hosts")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Hosts {
    @Id
    @GeneratedValue
    @Column(name = "Host_num")
    private Integer hostNum;

    @ManyToOne
    @JoinColumn(name = "User_num", referencedColumnName = "User_num")
    private User user;

    public Hosts(Integer hostNum, Integer userNum) {
        this.hostNum = hostNum;
        this.user = user;
    }
}
