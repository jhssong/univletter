package com.jhssong.univletter.domain.article.exception;

import org.springframework.http.HttpStatus;

public class ArticleExceptionUtils {

    public static ArticleException CrawlingError(String url, String errorMessage) {
        String message = "url=" + url + ", error=" + errorMessage;
        return new ArticleException(HttpStatus.INTERNAL_SERVER_ERROR, "크롤링 중 에러가 발생했습니다.", message, true);
    }
}
