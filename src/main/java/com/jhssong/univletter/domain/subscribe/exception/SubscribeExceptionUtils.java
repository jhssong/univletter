package com.jhssong.univletter.domain.subscribe.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class SubscribeExceptionUtils {

    public static BusinessException SubscriptionNotFound() {
        return new BusinessException(HttpStatus.NOT_FOUND, "구독 정보가 존재하지 않습니다.");
    }
}
