package com.jhssong.univletter.global.mail.exception;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EmailExceptionUtils {

    public static BusinessException AuthenticationFailedException(String email, String errorMessage) {
        String message = "SMTP 인증 실패 (to: " + email + "): " + errorMessage;
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static BusinessException MessagingException(String email, String errorMessage) {
        String message = "이메일 메시지 구성 오류 (to: " + email + "): " + errorMessage;
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static BusinessException Exception(String email, String errorMessage) {
        String message = "이메일 전송 중 알 수 없는 오류 발생 (to: " + email + "): " + errorMessage;
        return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
