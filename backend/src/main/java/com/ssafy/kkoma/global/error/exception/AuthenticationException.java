package com.ssafy.kkoma.global.error.exception;


import com.ssafy.kkoma.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
