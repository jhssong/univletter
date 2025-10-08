package com.jhssong.univletter.domain.board.exception;

import com.jhssong.errorping.exception.BaseDomainException;
import org.springframework.http.HttpStatus;

public class BoardException extends BaseDomainException {
    public BoardException(HttpStatus status, String message) {
        super(status, message);
    }
}
