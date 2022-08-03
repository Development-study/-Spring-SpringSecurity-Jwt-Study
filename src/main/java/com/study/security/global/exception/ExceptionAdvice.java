package com.study.security.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionDto> handleException(BusinessException ex) {
        return new ResponseEntity<ExceptionDto>(new ExceptionDto(ex.getStatus().value(), ex.getMessage()), ex.getStatus());
    }

}
