package com.jhssong.univletter.domain.subscribe.exception;

import com.jhssong.errorping.exception.BaseDomainException;
import org.springframework.http.HttpStatus;

public class SubscribeException extends BaseDomainException {
    public SubscribeException(HttpStatus status, String message) {
        super(status, message);
    }
}
