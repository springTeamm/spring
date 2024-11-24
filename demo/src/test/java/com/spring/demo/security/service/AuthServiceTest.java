package com.spring.demo.security.service;

import com.spring.demo.security.dto.AuthRequest;
import com.spring.demo.security.dto.AuthResponse;
import com.spring.demo.security.dto.RegisterHostRequest;
import com.spring.demo.security.dto.RegisterUserRequest;
import com.spring.demo.security.model.HostEntity;
import com.spring.demo.security.model.HostInfoEntity;
import com.spring.demo.security.model.UserEntity;
import com.spring.demo.security.repository.HostInfoRepository;
import com.spring.demo.security.repository.HostRepository;
import com.spring.demo.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private HostRepository hostRepository;

    @MockBean
    private HostInfoRepository hostInfoRepository;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전 필요한 설정을 합니다.
        userRepository.deleteAll();
        hostRepository.deleteAll();
        hostInfoRepository.deleteAll();
    }

    @Test
    void registerUserTest() {
        // given
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUserId("testuser");
        request.setPassword("password123");
        request.setEmail("test@example.com");
        request.setName("Test User");
        request.setPhone("01012345678");

        // when
        authService.registerUser(request);

        // then
        UserEntity savedUser = userRepository.findByEmail("test@example.com")
                .orElseThrow();

        assertThat(savedUser.getUserId()).isEqualTo("testuser");
        assertThat(savedUser.getName()).isEqualTo("Test User");
        assertThat(savedUser.getUserRights()).isEqualTo("USER");
    }

    @Test
    void registerHostTest() {
        // given
        RegisterHostRequest request = new RegisterHostRequest();
        request.setUserId("testhost");
        request.setPassword("password123");
        request.setEmail("host@example.com");
        request.setName("Test Host");
        request.setPhone("01012345678");
        request.setBusinessAddress("서울시 강남구");
        request.setBusinessType("개인");
        request.setCompanyName("테스트 회사");
        request.setRegistrationNumber("123-45-67890");
        request.setOpenDate(LocalDateTime.now());
        request.setTaxType("일반과세자");

        // when
        authService.registerHost(request);

        // then
        UserEntity savedUser = userRepository.findByEmail("host@example.com")
                .orElseThrow();
        HostEntity savedHost = hostRepository.findByUser(savedUser)
                .orElseThrow();
        HostInfoEntity savedHostInfo = hostInfoRepository.findByHost(savedHost)
                .orElseThrow();

        assertThat(savedUser.getUserRights()).isEqualTo("HOST");
        assertThat(savedHostInfo.getCompanyName()).isEqualTo("테스트 회사");
        assertThat(savedHostInfo.getRegistrationNumber()).isEqualTo("123-45-67890");
    }

    @Test
    void loginTest() {
        // given
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        // ... set up register request
        authService.registerUser(registerRequest);

        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // when
        AuthResponse response = authService.login(loginRequest);

        // then
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getUserRole()).isEqualTo("USER");
    }
}
