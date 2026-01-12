package com.jhssong.univletter.global.auth.exception;

import com.jhssong.univletter.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AuthException extends CustomException {
    public AuthException(HttpStatus status, String title, String message, boolean shouldAlert) {
        super(status, title, message, shouldAlert);
    }
}
