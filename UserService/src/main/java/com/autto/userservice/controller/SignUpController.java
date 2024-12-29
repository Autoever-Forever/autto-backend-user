package com.autto.userservice.controller;

import com.autto.userservice.dto.SignUpDto;
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
}
