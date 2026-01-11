package com.jhssong.univletter.domain.board.exception;

import com.jhssong.univletter.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class BoardException extends CustomException {
    public BoardException(HttpStatus status, String title, String message, boolean shouldAlert) {
        super(status, title, message, shouldAlert);
    }
}
