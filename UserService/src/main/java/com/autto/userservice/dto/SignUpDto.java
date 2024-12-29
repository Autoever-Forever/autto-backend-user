package com.autto.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "ID를 입력해주세요.")
    private String userId;
    @NotBlank(message = "PW 입력 바람~")
    private String userPw;
    @NotBlank(message = "PW 확인 바람~")
    private String confirmPassword;
}
