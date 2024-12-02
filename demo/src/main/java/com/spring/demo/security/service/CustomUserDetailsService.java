package com.spring.demo.security.service;

import com.spring.demo.security.entity.User;
import com.spring.demo.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 사용자 ID로 사용자 조회
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }

        // 사용자 권한 설정 (예: 호스트인지 일반 사용자인지)
        SimpleGrantedAuthority authority =
                user.getHosts() != null && !user.isHostsEmpty()
                        ? new SimpleGrantedAuthority("ROLE_HOST")
                        : new SimpleGrantedAuthority("ROLE_USER");

        // Spring Security UserDetails 객체로 변환
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPassword(),
                Collections.singletonList(authority)
        );
    }
}
