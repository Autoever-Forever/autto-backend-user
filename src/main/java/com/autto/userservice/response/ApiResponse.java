package com.autto.userservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 제외
public class ApiResponse<T> {
    private boolean isSuccess; // 요청 성공 여부
    private int status; // HTTP 상태 코드
    private T data; // 성공 시 데이터를 실패 시 에러 정보를 담는 필드
}
