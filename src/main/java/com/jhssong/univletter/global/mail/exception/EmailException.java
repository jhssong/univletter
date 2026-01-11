package com.jhssong.univletter.global.mail.exception;

import com.jhssong.univletter.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class EmailException extends CustomException {
    public EmailException(HttpStatus status, String title, String message, boolean shouldAlert) {
        super(status, title, message, shouldAlert);
    }
}
