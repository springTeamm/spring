package com.spring.demo.security.model;

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

    @Column(name = "User_num")
    private Integer userNum;

    public Hosts(Integer hostNum, Integer userNum) {
        this.hostNum = hostNum;
        this.userNum = userNum;
    }
}