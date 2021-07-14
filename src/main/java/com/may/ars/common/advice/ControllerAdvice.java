package com.may.ars.common.advice;
import com.may.ars.common.advice.exception.BusinessException;
import com.may.ars.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto<?>> businessException(final BusinessException e) {
        log.error("Business Exception : " + e.getMessage());
        final ExceptionCode exceptionCode = e.getExceptionCode();
        return new ResponseEntity<>(ResponseDto.fail(e.getMessage()), HttpStatus.valueOf(exceptionCode.getStatus()));
    }
}