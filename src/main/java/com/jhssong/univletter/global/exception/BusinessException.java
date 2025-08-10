package com.jhssong.univletter.global.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseDomainException {
    public BusinessException(HttpStatus status, String message) {
        super(status, message);
    }
}
