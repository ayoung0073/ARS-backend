package com.may.ars.common.advice.exception;

import com.may.ars.common.advice.ExceptionCode;

public class JsonWriteException extends BusinessException {
    public JsonWriteException() {
        super(ExceptionCode.JSON_WRITE_ERROR);
    }
}
