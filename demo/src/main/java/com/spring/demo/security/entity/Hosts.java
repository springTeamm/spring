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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Host_num")
    private Integer hostNum; // 호스트 고유 번호 (PK)

    @OneToOne
    @JoinColumn(name = "User_num")
    private User user; // 호스트는 User와 1:1 관계 (User 엔티티와 연결됨)

    @OneToOne(mappedBy = "host", cascade = CascadeType.ALL)
    private HostInfo hostInfo; // HostInfo와 양방향 관계 설정

    public Hosts(User user) {
        this.user = user; // 호스트와 연결된 사용자 정보
    }
}
