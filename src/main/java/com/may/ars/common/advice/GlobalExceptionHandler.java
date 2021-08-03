package com.may.ars.common.advice;
import com.may.ars.common.advice.exception.BusinessException;
import com.may.ars.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto<?>> businessException(final BusinessException e) {
        log.error("Business Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = e.getExceptionCode();
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }

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