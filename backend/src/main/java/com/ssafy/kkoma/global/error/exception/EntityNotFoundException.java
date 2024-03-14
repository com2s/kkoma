package com.ssafy.kkoma.global.error.exception;

import com.ssafy.kkoma.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
