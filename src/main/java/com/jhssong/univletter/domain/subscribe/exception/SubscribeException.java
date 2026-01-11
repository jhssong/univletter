package com.jhssong.univletter.domain.subscribe.exception;

import com.jhssong.univletter.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class SubscribeException extends CustomException {
    public SubscribeException(HttpStatus status, String title, String message, boolean shouldAlert) {
        super(status, title, message, shouldAlert);
    }
}
