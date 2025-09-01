package com.jhssong.univletter.global.config.security.auth.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthExceptionUtils {

    public static BusinessException TokenExpired() {
        return new BusinessException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
    }

    public static BusinessException TokenInvalid() {
        return new BusinessException(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");
    }

    public static BusinessException Unauthorized() {
        return new BusinessException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다.");
    }

}
