package com.study.security.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter @AllArgsConstructor
@Builder
public class ExceptionDto {

    private final int status;
    private final String message;

}
