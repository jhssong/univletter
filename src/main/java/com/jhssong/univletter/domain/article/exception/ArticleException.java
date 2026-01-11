package com.jhssong.univletter.domain.article.exception;

import com.jhssong.univletter.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ArticleException extends CustomException {
    public ArticleException(HttpStatus status, String title, String message, boolean shouldAlert) {
        super(status, title, message, shouldAlert);
    }
}
