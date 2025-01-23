package com.autto.userservice.controller;

import com.autto.userservice.dto.SignUpDto;
import com.autto.userservice.service.EmailVerificationService;
import com.autto.userservice.service.SignUpService;
import com.autto.userservice.dto.SignUpResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    private final EmailVerificationService emailVerificationService;

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<SignUpResponseDto> checkEmail(@RequestParam @Email String email) {
        SignUpResponseDto response = signUpService.checkEmailDuplicate(email);
        return ResponseEntity.ok(response);
    }

    // 인증 이메일 전송
    @PostMapping("/send-verification")
    public ResponseEntity<SignUpResponseDto> sendVerificationEmail(@RequestParam @Email String email) {
        SignUpResponseDto response = signUpService.sendVerificationEmail(email);
        return ResponseEntity.ok(response);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        SignUpResponseDto response = signUpService.signUp(signUpDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<SignUpResponseDto> verifyCode(@RequestParam @Email String email,
                                                        @RequestParam String verificationCode) {
        boolean isVerified = emailVerificationService.verifyCode(email, verificationCode);

        if (isVerified) {
            return ResponseEntity.ok(SignUpResponseDto.setSuccess("인증되었습니다"));
        } else {
            return ResponseEntity.badRequest().body(SignUpResponseDto.setFailed("인증 코드가 올바르지 않습니다."));
        }
    }
}
