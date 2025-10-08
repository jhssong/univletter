package com.jhssong.univletter.global.mail.exception;

import com.jhssong.errorping.exception.BaseDomainException;
import org.springframework.http.HttpStatus;

public class EmailException extends BaseDomainException {
    public EmailException(HttpStatus status, String message) {
        super(status, message);
    }
}
