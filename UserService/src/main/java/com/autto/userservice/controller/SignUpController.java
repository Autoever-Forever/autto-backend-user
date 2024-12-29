package com.autto.userservice.controller;

import com.autto.userservice.dto.SignUpDto;
import com.autto.userservice.dto.SignUpResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.autto.userservice.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private SignUpService SignUpService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> SignUp(@RequestBody SignUpDto SignUpDto) {
        try {
            SignUpResponseDto response = SignUpService.registerUser(SignUpDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(SignUpResponseDto.setFailed("회원가입에 실패했습니다."));
        }
    }
}
