package com.hibicode.beerstore.service.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(Object... args) {
        super("error-2", HttpStatus.NOT_FOUND, args);
    }

    public EntityNotFoundException(String errorCode, Object... args) {
        super(errorCode, HttpStatus.NOT_FOUND, args);
    }


}
