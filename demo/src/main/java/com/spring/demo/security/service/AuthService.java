package com.spring.demo.security.service;

import com.spring.demo.security.dto.AuthRequest;
import com.spring.demo.security.dto.AuthResponse;
import com.spring.demo.security.dto.RegisterHostRequest;
import com.spring.demo.security.dto.RegisterUserRequest;
import com.spring.demo.security.model.HostEntity;
import com.spring.demo.security.model.HostInfoEntity;
import com.spring.demo.security.model.Role;
import com.spring.demo.security.model.UserEntity;
import com.spring.demo.security.repository.HostInfoRepository;
import com.spring.demo.security.repository.HostRepository;
import com.spring.demo.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.hostRepository = hostRepository;
        this.hostInfoRepository = hostInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            // 이메일이 이미 사용 중임을 처리하는 로직 (예: 로그 기록)
            System.out.println("Email is already in use");
            return;
        }

        if (userRepository.existsByUserId(request.getUserId())) {
            // 사용자 ID가 이미 사용 중임을 처리하는 로직 (예: 로그 기록)
            System.out.println("User ID is already in use");
            return;
        }

        UserEntity user = new UserEntity();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setNickName(request.getNickName());
        user.setPhone(request.getPhone());
        user.setUserRights("USER");

        userRepository.save(user);
    }

    public void registerHost(RegisterHostRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            // 이메일이 이미 사용 중임을 처리하는 로직 (예: 로그 기록)
            System.out.println("Email is already in use");
            return;
        }

        if (userRepository.existsByUserId(request.getUserId())) {
            // 사용자 ID가 이미 사용 중임을 처리하는 로직 (예: 로그 기록)
            System.out.println("User ID is already in use");
            return;
        }

        // 사용자 정보 저장
        UserEntity user = new UserEntity();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setNickName(request.getNickName());
        user.setPhone(request.getPhone());
        user.setUserRights("HOST");
        userRepository.save(user);

        // 호스트 정보 저장
        HostEntity host = new HostEntity();
        host.setUser(user);
        hostRepository.save(host);

        // 호스트 상세 정보 저장
        HostInfoEntity hostInfo = new HostInfoEntity();
        hostInfo.setHost(host);
        hostInfo.setBusinessAddress(request.getBusinessAddress());
        hostInfo.setBusinessItem(request.getBusinessItem());
        hostInfo.setRegistrationNumber(request.getRegistrationNumber());
        hostInfo.setBusinessType(request.getBusinessType());
        hostInfo.setCompanyName(request.getCompanyName());
        hostInfo.setCorporateName(request.getCorporateName());
        hostInfo.setCorporateNumber(request.getCorporateNumber());
        hostInfo.setOpenDate(request.getOpenDate());
        hostInfo.setTaxType(request.getTaxType());
        hostInfo.setRegistDate(LocalDateTime.now());

        hostInfoRepository.save(hostInfo);
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 이메일을 통해 토큰 생성
        String token = jwtService.generateToken(user.getEmail());

        // 수정된 생성자 사용
        return new AuthResponse(token, user.getUserRights());
    }

}
