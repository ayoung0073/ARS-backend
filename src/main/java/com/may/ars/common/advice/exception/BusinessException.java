package com.may.ars.common.advice.exception;

import com.may.ars.common.advice.ExceptionCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}