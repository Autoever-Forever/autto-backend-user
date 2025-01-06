package com.autto.userservice.response;

import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
public class CustomUsernameNotFoundException extends UsernameNotFoundException {

    private String additionalInfo;
    private final ErrorCode errorCode;

    public CustomUsernameNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}