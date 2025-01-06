package com.autto.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
@Getter
public class ErrorDto {
    private String code; // 커스텀 에러 코드
    private String message; // 에러 메시지
    private String exceptionClass; // 예외 클래스 이름
    private Object errors; // 추가 오류 정보 (필요에 따라 사용)
}
