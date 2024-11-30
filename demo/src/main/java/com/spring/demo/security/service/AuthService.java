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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // 생성자 주입
    public AuthService(UserRepository userRepository,
                       HostRepository hostRepository,
                       HostInfoRepository hostInfoRepository,
                       @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.hostRepository = hostRepository;
        this.hostInfoRepository = hostInfoRepository;
        this.passwordEncoder = passwordEncoder; // 초기화
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(RegisterUserRequest request) {
        if (isEmailOrUserIdExists(request.getEmail(), request.getUserId())) return;

        User user = createUserFromRegisterUserRequest(request, "USER");
        userRepository.save(user);
    }

    public void registerHost(RegisterHostRequest request) {
        if (isEmailOrUserIdExists(request.getEmail(), request.getUserId())) return;

        // 사용자 정보 저장
        User user = createUserFromRegisterHostRequest(request, "HOST");
        userRepository.save(user);

        // 호스트 정보 저장
        Hosts host = new Hosts();
        host.setUser(user);
        hostRepository.save(host);

        // 호스트 상세 정보 저장
        HostInfo hostInfo = createHostInfoFromRequest(request, host);
        hostInfoRepository.save(hostInfo);
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

        String token = jwtService.generateToken(user.getUserEmail());
        return new AuthResponse(token, user.getUserRights());
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
}