package com.jhssong.univletter.domain.article.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ArticleExceptionUtils {

    public static BusinessException CrawlingError(String url, String errorMessage) {
        String message = "크롤링 중 에러가 발생했습니다. url=" + url + ", error=" + errorMessage;
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
