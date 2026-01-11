package com.jhssong.univletter.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private HttpStatus status;
    private String title;
    private String message;
    private boolean shouldAlert;

    public CustomException(HttpStatus status, String title, String message, boolean shouldAlert) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.shouldAlert = shouldAlert;
    }
}
