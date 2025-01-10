package com.autto.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    //영진추가 - 로그인 성공시 사용자의 이메일과 이름 반환
    private String email;
    private String name;
}
