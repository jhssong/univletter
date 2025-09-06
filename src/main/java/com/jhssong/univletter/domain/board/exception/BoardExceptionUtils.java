package com.jhssong.univletter.domain.board.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class BoardExceptionUtils {

    public static BusinessException BoardNotFound(String boardName, String boardSubName) {
        String message = "존재하지 않는 게시판입니다. (" + boardName + " - " + boardSubName + ")";
        return new BusinessException(HttpStatus.NOT_FOUND, message);
    }
}
