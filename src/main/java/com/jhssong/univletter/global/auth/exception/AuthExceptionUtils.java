package com.jhssong.univletter.global.auth.exception;

import org.springframework.http.HttpStatus;

public class AuthExceptionUtils {
    public static AuthException Unauthorized() {
        return new AuthException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다.", "", false);
    }

}
