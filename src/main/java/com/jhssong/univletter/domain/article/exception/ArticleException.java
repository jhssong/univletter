package com.jhssong.univletter.domain.article.exception;

import com.jhssong.errorping.exception.BaseDomainException;
import org.springframework.http.HttpStatus;

public class ArticleException extends BaseDomainException {
    public ArticleException(HttpStatus status, String message) {
        super(status, message);
    }
}
