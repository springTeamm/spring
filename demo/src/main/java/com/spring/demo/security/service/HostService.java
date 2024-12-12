package com.spring.demo.security.service;

import com.spring.demo.security.dto.HostDTO;
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
public class HostService {

    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final HostInfoRepository hostInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // 호스트 회원가입 메서드
    @Transactional
    public User registerHost(HostDTO hostDTO) {
        // 비밀번호 일치 확인
        if (!hostDTO.getUserPassword().equals(hostDTO.getConfirmUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자 ID와 이메일 중복 검증
        if (userRepository.existsByUserId(hostDTO.getUserId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByUserEmail(hostDTO.getUserEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 사업자 등록번호 중복 검증
        if (hostInfoRepository.findByHostRegistNum(hostDTO.getBusinessRegistrationNumber()).isPresent()) {
            throw new RuntimeException("이미 등록된 사업자 번호입니다.");
        }

        // 비밀번호 암호화
        hostDTO.setUserPassword(passwordEncoder.encode(hostDTO.getUserPassword()));

        // 사용자 엔티티 생성
        User user = modelMapper.map(hostDTO, User.class);
        user.setUserRights("HOST"); // 호스트 권한 부여
        User savedUser = userRepository.save(user);

        // 호스트 엔티티 생성
        Hosts host = new Hosts();
        host.setUser(savedUser);
        Hosts savedHost = hostRepository.save(host);

        // 호스트 정보 엔티티 생성
        HostInfo hostInfo = modelMapper.map(hostDTO, HostInfo.class);
        hostInfo.setHost(savedHost);
        hostInfo.setHostRegistDate(java.time.LocalDateTime.now());
        hostInfoRepository.save(hostInfo);

        userRepository.flush(); // DB에 강제로 반영
        return savedUser;
    }

    // 호스트 정보 업데이트 메서드
    @Transactional
    public void updateHostInfo(Integer userNum, HostDTO hostDTO) {
        // 기존 호스트 정보 조회
        Hosts host = hostRepository.findByUser_UserNum(userNum);

        if (host == null) {
            throw new RuntimeException("호스트 정보를 찾을 수 없습니다.");
        }

        // 호스트 정보 엔티티 업데이트
        HostInfo hostInfo = hostInfoRepository.findByHost_HostNum(host.getHostNum());

        if (hostInfo == null) {
            throw new RuntimeException("호스트 상세 정보를 찾을 수 없습니다.");
        }

        // DTO의 정보로 호스트 정보 업데이트
        modelMapper.map(hostDTO, hostInfo);
        hostInfo.setHostModifyDate(java.time.LocalDateTime.now());

        hostInfoRepository.save(hostInfo);
    }

    // 호스트 정보 조회 메서드
    public HostDTO getHostInfo(Integer userNum) {
        Hosts host = hostRepository.findByUser_UserNum(userNum);

        if (host == null) {
            throw new RuntimeException("호스트 정보를 찾을 수 없습니다.");
        }

        HostInfo hostInfo = hostInfoRepository.findByHost_HostNum(host.getHostNum());

        if (hostInfo == null) {
            throw new RuntimeException("호스트 상세 정보를 찾을 수 없습니다.");
        }

        return modelMapper.map(hostInfo, HostDTO.class);
    }

}