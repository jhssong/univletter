package com.jhssong.univletter.domain.board;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class BoardExceptionUtils {

    public static BusinessException BoardNotFound() {
        return new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.");
    }
}
