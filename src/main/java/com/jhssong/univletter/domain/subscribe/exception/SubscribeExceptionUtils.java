package com.jhssong.univletter.domain.subscribe.exception;

import org.springframework.http.HttpStatus;

public class SubscribeExceptionUtils {

    public static SubscribeException SubscriptionNotFound() {
        return new SubscribeException(HttpStatus.NOT_FOUND, "구독 정보가 존재하지 않습니다.");
    }
}
