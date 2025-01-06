package com.autto.userservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomUsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> handleUsernameNotFoundException(CustomUsernameNotFoundException e) {

        // ErrorDto 객체 생성
        ErrorDto errorDto = ErrorDto.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .exceptionClass(e.getClass().getName())
                .errors("")
                .build();

        // 400번 상태 코드 (Bad Request)
        return ResponseEntity.badRequest().body(ApiResponse.<ErrorDto>builder()  // ApiResponse<ErrorDto> 설정
                        .isSuccess(false)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .data(errorDto)
                        .build());

    }
}
