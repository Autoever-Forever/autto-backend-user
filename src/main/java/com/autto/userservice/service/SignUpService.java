package com.autto.userservice.service;

import com.autto.userservice.dto.SignUpDto;
import com.autto.userservice.dto.SignUpResponseDto;
import com.autto.userservice.entity.User;
import com.autto.userservice.persistence.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;

//회원가입 전체 프로세스
@Data
@Service
public class SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);
    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpService(UserRepository userRepository, EmailVerificationService emailVerificationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
        this.passwordEncoder = passwordEncoder;
    }
    //이메일 중복 확인
    public SignUpResponseDto checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            return SignUpResponseDto.setFailed("이메일이 이미 사용 중입니다.");
        }
        return SignUpResponseDto.setSuccess("사용 가능한 이메일입니다.");
    }

    //이메일 인증 전송
    public SignUpResponseDto sendVerificationEmail(String email) {
        String verificationCode = emailVerificationService.sendVerificationCode(email);
        return SignUpResponseDto.setSuccessData("인증 코드가 전송되었습니다.", verificationCode);
    }

    //회원가입 로직
    public SignUpResponseDto signUp(SignUpDto signUpDto) {
        if (!signUpDto.getUserPw().equals(signUpDto.getConfirmPassword())) {
            return SignUpResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        boolean isVerified = emailVerificationService.verifyCode(signUpDto.getEmail(), signUpDto.getVerificationCode());
        if (!isVerified) {
            return SignUpResponseDto.setFailed("인증 코드가 올바르지 않습니다.");
        }


        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return SignUpResponseDto.setFailed("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getUserPw());

        //회원 정보 저장 -> 회원가입 성공
        User user = User.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .userPw(encodedPassword)
                .role(User.Role.USER)
                .userStatus(User.UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        //인증코드 삭제
        emailVerificationService.removeVerificationCode(signUpDto.getEmail());

        // 성공 응답에 사용자 이름과 이메일 포함
        Map<String, String> userData = new HashMap<>();
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        //UUID확인용
        //userData.put("id", user.getUuidHex());

        return SignUpResponseDto.setSuccessData("회원가입이 성공적으로 완료되었습니다.", userData);
    }
}
