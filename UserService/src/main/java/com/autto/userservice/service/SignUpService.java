package com.autto.userservice.service;

import com.autto.userservice.dto.SignUpDto;
import com.autto.userservice.dto.SignUpResponseDto;
import com.autto.userservice.entity.User;
import com.autto.userservice.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto registerUser(SignUpDto SignUpDto) {
        // 비밀번호 확인
        if (!SignUpDto.getUserPw().equals(SignUpDto.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        // 기존 회원가입 로직 수행 후
        User userEntity = registerUserInDb(SignUpDto);

        return SignUpResponseDto.setSuccessData("회원가입 성공", userEntity);
    }

    @Transactional
    public User registerUserInDb(SignUpDto SignUpDto) {
        // 이미 등록된 사용자 아이디가 있는지 확인
        if (userRepository.existsByUserId(SignUpDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(SignUpDto.getUserPw());

        // UserEntity 생성
        User userEntity = User.builder()
                .userId(SignUpDto.getUserId())
                .userPw(encodedPassword) // 암호화된 비밀번호 저장
                .name(SignUpDto.getName())
                .role(User.Role.MEMBER) // 기본 역할 'member'
                .userStatus(User.UserStatus.ACTIVE) // 기본 상태 'active'
                .build();

        // DB에 저장
        User savedUser = userRepository.save(userEntity);
        logger.info("User registered successfully: userId={}", savedUser.getUserId());
        return savedUser;
    }
}
