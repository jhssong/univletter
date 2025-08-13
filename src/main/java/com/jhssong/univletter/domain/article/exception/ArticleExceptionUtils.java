package com.jhssong.univletter.domain.article.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ArticleExceptionUtils {

    public static BusinessException NoticeAlreadyExists() {
        return new BusinessException(HttpStatus.CONFLICT, "이미 존재하는 공지입니다.");
    }
}
