package com.hibicode.beerstore.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BusinessException extends  RuntimeException {

    private final String errorCode;
    private final HttpStatus status;
    private final Object[] args;

}
