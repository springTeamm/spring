package com.spring.demo.security.service;

import com.spring.demo.security.dto.HostDTO;
import com.spring.demo.security.dto.UserDTO;
import com.spring.demo.security.entity.HostInfo;
import com.spring.demo.security.entity.Hosts;
import com.spring.demo.security.entity.User;
import com.spring.demo.security.repository.HostInfoRepository;
import com.spring.demo.security.repository.HostRepository;
import com.spring.demo.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final HostInfoRepository hostInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // 일반 사용자 회원가입 메서드
    @Transactional
    public User registerUser(UserDTO userDTO) {
        // 입력 유효성 검증
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 일치 확인
        if (!userDTO.getUserPassword().equals(userDTO.getConfirmUserPwd())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        userDTO.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));

        // DTO를 엔티티로 변환 및 저장
        User user = modelMapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    // 호스트 사용자 회원가입 메서드
    @Transactional
    public User registerHost(HostDTO hostDTO) {
        // 일반 사용자 회원가입 로직 실행
        User user = registerUser(hostDTO);

        // 호스트 정보 생성
        Hosts host = new Hosts();
        host.setUser(user);
        Hosts savedHost = hostRepository.save(host);

        // 호스트 상세 정보 생성
        HostInfo hostInfo = modelMapper.map(hostDTO, HostInfo.class);
        hostInfo.setHost(savedHost);
        hostInfoRepository.save(hostInfo);

        return user;
    }

    // 사용자 로그인 검증 메서드
    public boolean validateUser(String userId, String password) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getUserPassword());
    }
}
