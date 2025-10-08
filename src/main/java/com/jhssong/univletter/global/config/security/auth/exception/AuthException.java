package com.jhssong.univletter.global.config.security.auth.exception;

import com.jhssong.errorping.exception.BaseDomainException;
import org.springframework.http.HttpStatus;

public class AuthException extends BaseDomainException {
    public AuthException(HttpStatus status, String message) {
        super(status, message);
    }
}
