package com.may.ars.common.advice;
import com.may.ars.common.advice.exception.BusinessException;
import com.may.ars.dto.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto<?>> businessException(final BusinessException e) {
        log.error("Business Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = e.getExceptionCode();
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    /*
     * javax.validation.Valid or @Validated 으로 binding error 발생할 경우
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못 할 경우 발생
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseDto<?>> httpMessageNotReadableException() {
        log.error("HttpMessageNotReadableException");
        final ExceptionCode exceptionCode = ExceptionCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<ResponseDto<?>> servletException(final ServletException e) {
        log.error("ServletException Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.SERVLET_ERROR;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생 시 발생
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할 경우 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    /**
     * 예상치 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto<?>> handleException(final Exception e) {
        log.error("Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = ExceptionCode.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ResponseDto.fail(exceptionCode.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

}