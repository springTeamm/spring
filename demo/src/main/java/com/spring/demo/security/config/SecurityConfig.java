package com.spring.demo.security.config;

import com.spring.demo.security.service.CustomUserDetailsService;
import com.spring.demo.security.service.LoginProvider;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 활성화
                .csrf(csrf -> csrf.disable()) // 개발 중엔 비활성화 (운영 시 주석 해제)
                // CORS 활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 접근 권한 설정
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login/**", "/signup/**", "/css/**").permitAll() // 공개 경로
                        .requestMatchers("/user/**").hasRole("USER") // 사용자 전용 경로
//                        .requestMatchers("/host/**").hasRole("HOST") // 호스트 전용 경로
                        .requestMatchers("/host/**").permitAll() // 호스트 경로 전체 허용
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginProcessingUrl("/login/**")  // 사용자 로그인\
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            //1. 세션 초기화
                            HttpSession session = request.getSession(false);
                            //2. id 추출
                            String userId = authentication.getPrincipal().toString();
                            //3. 유저정보 세션에 저장 후 세션 지속시간 설정(60분)
                            session.setAttribute("UserInfo", userId);
                            session.setMaxInactiveInterval(3600);

                            String loginPath = request.getRequestURI();
                            if (loginPath.equals("/login/user")) {
                                log.info("사용자 로그인 성공: {}", userId);
                            } else if (loginPath.equals("/login/host")) {
                                log.info("호스트 로그인 성공: {}", userId);
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            log.info("exception {}", exception.getMessage());
                        })
                        .permitAll()
                )
                // 로그아웃 설정(미사용)
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // 공통 로그아웃 처리
                        .logoutSuccessHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.invalidate(); //로그인 세션 제거
                        })
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                )
                // 인증 절차
                .authenticationProvider(loginProvider())
                // 세션 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    // 비밀번호 인코더 빈 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginProvider loginProvider() {
        return new LoginProvider(customUserDetailsService, passwordEncoder());
    }

    //cors config
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4000");  // React 클라이언트의 URL
        config.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        config.addAllowedHeader("*");  // 모든 헤더 허용
        config.setAllowCredentials(true);  // 쿠키 포함 허용
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}