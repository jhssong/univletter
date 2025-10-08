package com.jhssong.univletter.domain.board.exception;

import org.springframework.http.HttpStatus;

public class BoardExceptionUtils {

    public static BoardException BoardNotFound(String boardName, String boardSubName) {
        String message = "존재하지 않는 게시판입니다. (" + boardName + " - " + boardSubName + ")";
        return new BoardException(HttpStatus.NOT_FOUND, message);
    }
}
