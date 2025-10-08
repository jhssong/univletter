package com.jhssong.univletter.global.mail.exception;

import org.springframework.http.HttpStatus;

public class EmailExceptionUtils {

    public static EmailException AuthenticationFailedException(String email, String errorMessage) {
        String message = "SMTP 인증 실패 (to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static EmailException MessagingException(String email, String errorMessage) {
        String message = "이메일 메시지 구성 오류 (to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static EmailException Exception(String email, String errorMessage) {
        String message = "이메일 전송 중 알 수 없는 오류 발생 (to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
