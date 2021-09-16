package com.may.ars.common.advice;
import com.may.ars.common.advice.exception.BusinessException;
import com.may.ars.dto.common.ResponseDto;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * javax.validation.Valid or @Validated 으로 binding error 발생할 경우
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못 할 경우 발생
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseDto<?>> httpMessageNotReadableException() {
        log.error("HttpMessageNotReadableException");
        final ExceptionCode exceptionCode = ExceptionCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), exceptionCode.getMessage()), exceptionCode.getStatus());
    }

    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<ResponseDto<?>> servletException(final ServletException e) {
        log.error("ServletException Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.SERVLET_ERROR;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), exceptionCode.getMessage()), exceptionCode.getStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ResponseDto<?>> handleMethodArgumentException() {
        log.error("MethodArgumentException");
        final ExceptionCode exceptionCode = ExceptionCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), exceptionCode.getMessage()), exceptionCode.getStatus());
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ResponseDto<?>> signatureException(SignatureException e) {
        log.error("SignatureException : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.JWT_EXCEPTION;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), exceptionCode.getMessage()), exceptionCode.getStatus());
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto<?>> businessException(final BusinessException e) {
        log.error("Business Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = e.getExceptionCode();
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), e.getMessage()), exceptionCode.getStatus());
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto<?>> handleException(final Exception e) {
        log.error("Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getStatus(), exceptionCode.getMessage()), exceptionCode.getStatus());
    }

}