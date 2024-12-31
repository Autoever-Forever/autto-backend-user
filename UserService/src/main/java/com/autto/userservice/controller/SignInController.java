package com.autto.userservice.controller;

import com.autto.userservice.dto.JwtToken;
import com.autto.userservice.dto.SignInDto;
import com.autto.userservice.service.SignInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SignInController {

    private final SignInService signInService;

    @GetMapping("/test")
    public String test() {
        return "서버 작동중";
    }

    @PostMapping("/signin")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = signInService.signIn(email, password);
        log.info("request email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }
}
