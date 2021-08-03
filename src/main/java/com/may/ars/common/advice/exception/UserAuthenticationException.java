package com.may.ars.common.advice.exception;

import com.may.ars.common.advice.ExceptionCode;

public class UserAuthenticationException extends BusinessException {
    public UserAuthenticationException() {
        super(ExceptionCode.NOT_VALID_USER);
    }
}
