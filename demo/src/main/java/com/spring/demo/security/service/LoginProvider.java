package com.spring.demo.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class LoginProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1. 입력받은 값에서 사용자 이름, 비밀번호 생성
        String username = authentication.getName() ; //id
        String password = authentication.getCredentials().toString(); //password

        //2. db에서 사용자 이름, 비밀번호 가져오기
        UserDetails user = customUserDetailsService.loadUserByUsername(username);

        //3. 비밀번호가 맞는지 틀리는지 확인
        if(!passwordEncoder.matches(password, user.getPassword())) {
            log.info("userId : {}, password : {}", username, password);
            throw new BadCredentialsException("비밀번호가 틀렸습니다.");
        }

        //4. 토큰 생성
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
