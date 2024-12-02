package com.spring.demo.security.service;

import com.spring.demo.security.dto.AuthRequest;
import com.spring.demo.security.dto.AuthResponse;
import com.spring.demo.security.dto.RegisterHostRequest;
import com.spring.demo.security.dto.RegisterUserRequest;
import com.spring.demo.security.model.HostInfo;
import com.spring.demo.security.model.Hosts;
import com.spring.demo.security.model.User;
import com.spring.demo.security.repository.HostInfoRepository;
import com.spring.demo.security.repository.HostRepository;
import com.spring.demo.security.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final HostInfoRepository hostInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;

    // 생성자 주입
    public AuthService(UserRepository userRepository,
                       HostRepository hostRepository,
                       HostInfoRepository hostInfoRepository,
                       @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       HttpSession httpSession) {
        this.userRepository = userRepository;
        this.hostRepository = hostRepository;
        this.hostInfoRepository = hostInfoRepository;
        this.passwordEncoder = passwordEncoder; // 초기화
        this.authenticationManager = authenticationManager;
        this.httpSession = httpSession;
    }

    public boolean registerUser(RegisterUserRequest request) {
        if (isEmailOrUserIdExists(request.getEmail(), request.getUserId())) {
            return true; // 이미 존재하면 true 반환
        }

        User user = createUserFromRegisterUserRequest(request, "USER");
        userRepository.save(user);
        return false; // 성공적으로 저장되면 false 반환
    }

    public boolean registerHost(RegisterHostRequest request) {
        if (isEmailOrUserIdExists(request.getEmail(), request.getUserId())) {
            return true; // 이미 존재하면 true 반환
        }

        User user = createUserFromRegisterHostRequest(request, "HOST");
        userRepository.save(user);

        Hosts host = new Hosts();
        host.setUser(user);
        hostRepository.save(host);

        HostInfo hostInfo = createHostInfoFromRequest(request, host);
        hostInfoRepository.save(hostInfo);
        return false; // 성공적으로 저장되면 false 반환
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("user", user);

        return new AuthResponse("Session established", user.getUserRights());
    }

    // 로그아웃 메서드 추가
    public void logout() {
        httpSession.invalidate(); // 세션 무효화
    }


    // 중복 제거를 위한 유틸리티 메서드
    private boolean isEmailOrUserIdExists(String email, String userId) {
        if (userRepository.existsByUserEmail(email)) {
            System.out.println("Email is already in use");
            return true;
        }

        if (userRepository.existsByUserId(userId)) {
            System.out.println("User ID is already in use");
            return true;
        }

        return false;
    }

    private User createUserFromRegisterUserRequest(RegisterUserRequest request, String userRights) {
        User user = new User();
        user.setUserId((request.getUserId()));
        user.setUserPwd(passwordEncoder.encode(request.getPassword()));
        user.setUserEmail(request.getEmail());
        user.setUserName(request.getName());
        user.setUserNickname(request.getNickName());
        user.setUserPhone(request.getPhone());
        user.setUserRights(userRights);
        return user;
    }

    private User createUserFromRegisterHostRequest(RegisterHostRequest request, String userRights) {
        User user = new User();
        user.setUserId((request.getUserId()));
        user.setUserPwd(passwordEncoder.encode(request.getPassword()));
        user.setUserEmail(request.getEmail());
        user.setUserName(request.getName());
        user.setUserNickname(request.getNickName());
        user.setUserPhone(request.getPhone());
        user.setUserRights(userRights);
        return user;
    }

    private HostInfo createHostInfoFromRequest(RegisterHostRequest request, Hosts host) {
        HostInfo hostInfo = new HostInfo();
        hostInfo.setHost(host);
        hostInfo.setHostBisAddress(request.getBusinessAddress());
        hostInfo.setHostBisItem(request.getBusinessItem());
        hostInfo.setHostRegistNum(request.getRegistrationNumber());
        hostInfo.setHostBisType(request.getBusinessType());
        hostInfo.setHostCompanyName(request.getCompanyName());
        hostInfo.setHostCorpName(request.getCorporateName());
        hostInfo.setHostCorpsocialNum(request.getCorporateNumber());
        hostInfo.setHostOpenDate(
                Date.from(request.getOpenDate().atZone(ZoneId.systemDefault()).toInstant())
        );
        hostInfo.setHostTaxType(request.getTaxType());
        hostInfo.setHostRegistDate(new Date());
        return hostInfo;
    }

    // 사용자 인증을 수행하는 메서드 추가
    public User authenticateUser(AuthRequest request) {
        // 이메일로 사용자 조회
        User user = userRepository.findByUserEmail(request.getEmail())
                .orElse(null);

        // 사용자가 존재하지 않거나 비밀번호가 일치하지 않는 경우 null 반환
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getUserPwd())) {
            return null;
        }

        // 인증된 사용자 객체 반환
        return user;
    }
}