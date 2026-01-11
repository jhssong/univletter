package com.jhssong.univletter.global.mail.exception;

import org.springframework.http.HttpStatus;

public class EmailExceptionUtils {

    public static EmailException AuthenticationFailedException(String email, String errorMessage) {
        String message = "(to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, "SMTP 인증 실패", message, true);
    }

    public static EmailException MessagingException(String email, String errorMessage) {
        String message = "(to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 메시지 구성 오류", message, true);
    }

    public static EmailException Exception(String email, String errorMessage) {
        String message = "(to: " + email + "): " + errorMessage;
        return new EmailException(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송 중 알 수 없는 오류 발생", message, true);
    }

}
