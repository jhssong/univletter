package com.jhssong.univletter.domain.board.exception;

import org.springframework.http.HttpStatus;

public class BoardExceptionUtils {

    public static BoardException BoardNotFound(String boardName, String boardSubName) {
        String message = boardName + " - " + boardSubName;
        return new BoardException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.", message, true);
    }
}
