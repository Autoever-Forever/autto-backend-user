package com.autto.userservice.controller;

import com.autto.userservice.dto.JwtToken;
import com.autto.userservice.provider.JwtTokenProvider;
import com.autto.userservice.response.ApiResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/check/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * JWT 토큰의 모든 클레임 정보를 반환합니다.
     *
     * @param token Authorization 헤더에 포함된 Bearer 토큰
     * @return 토큰의 클레임 정보
     */
    @GetMapping("/token-info")
    public ResponseEntity<ApiResponse<?>> getTokenInfo(@RequestHeader("Authorization") String token) {
        // Bearer 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 토큰에서 클레임 정보 추출
        Claims claims = jwtTokenProvider.parseClaims(token);

        // 클레임 정보를 반환
        return ResponseEntity.ok(ApiResponse.<Claims>builder()
                .isSuccess(true)
                .status(HttpStatus.OK.value())
                .data((Claims) claims)
                .build());
    }
}
