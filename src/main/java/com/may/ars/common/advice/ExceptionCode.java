package com.may.ars.common.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    // Common
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Entity Not Found"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "Invalid Method"),
    JSON_WRITE_ERROR(HttpStatus.BAD_REQUEST, "Json Write Error"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    SERVLET_ERROR(HttpStatus.BAD_REQUEST, "Servlet Error"),

    // JWT
    JWT_EXCEPTION(HttpStatus.BAD_REQUEST, "Jwt Exception"),
    NOT_VALID_USER(HttpStatus.BAD_REQUEST, "Authorization Exception");

    private final HttpStatus status;
    private final String message;

}