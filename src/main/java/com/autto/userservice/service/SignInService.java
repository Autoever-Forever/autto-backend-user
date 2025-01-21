package com.autto.userservice.service;

import com.autto.userservice.dto.JwtToken;

public interface SignInService {
    /**
     * 회원 로그인 및 JWT 토큰 발행 메서드.
     * @param email 사용자의 이메일 (로그인 ID)
     * @param password 사용자의 비밀번호
     * @return JWT 토큰 객체
     */
    JwtToken signIn(String email, String password);
}
