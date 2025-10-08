package com.jhssong.univletter.global.config.security.auth.exception;

import org.springframework.http.HttpStatus;

public class AuthExceptionUtils {

    public static AuthException TokenExpired() {
        return new AuthException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
    }

    public static AuthException TokenInvalid() {
        return new AuthException(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");
    }

    public static AuthException Unauthorized() {
        return new AuthException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다.");
    }

}
