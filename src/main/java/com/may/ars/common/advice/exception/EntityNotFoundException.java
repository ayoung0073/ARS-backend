package com.may.ars.common.advice.exception;

import com.may.ars.common.advice.ExceptionCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(ExceptionCode.ENTITY_NOT_FOUND);
    }
}