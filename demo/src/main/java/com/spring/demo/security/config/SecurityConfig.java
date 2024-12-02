package com.spring.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 접근 권한 설정
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login", "/signup/**", "/css/**").permitAll() // 공개 경로
                        .requestMatchers("/user/**").hasRole("USER") // 사용자 전용 경로
                        .requestMatchers("/host/**").hasRole("HOST") // 호스트 전용 경로
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )

                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 경로
                        .loginProcessingUrl("/login-process") // 로그인 처리 URL
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 이동 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동 경로
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )

                // CSRF 보호 활성화
                .csrf(csrf -> csrf.disable()); // 개발 중엔 비활성화 (운영 시 주석 해제)

        return http.build();
    }

    // 비밀번호 인코더 빈 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}