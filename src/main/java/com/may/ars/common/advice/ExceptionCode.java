package com.may.ars.common.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    // Common
    ENTITY_NOT_FOUND(400, "Entity Not Found"),
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(400, "Invalid Input Value"),
    JSON_WRITE_ERROR(400, "Json Write Error"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // JWT
    JWT_EXCEPTION(400, "Jwt Exception"),
    NOT_VALID_USER(400, "Authorization Exception");

    private final int status;
    private final String message;

}