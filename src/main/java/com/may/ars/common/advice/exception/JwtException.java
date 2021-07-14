package com.may.ars.common.advice.exception;

import com.may.ars.common.advice.ExceptionCode;

public class JwtException extends BusinessException {
    public JwtException() {
        super(ExceptionCode.JWT_EXCEPTION);
    }
}