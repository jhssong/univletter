package com.jhssong.univletter.domain.notice;

import com.jhssong.univletter.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NoticeExceptionUtils {

    public static BusinessException NoticeAlreadyExists() {
        return new BusinessException(HttpStatus.CONFLICT, "이미 존재하는 공지입니다.");
    }
}
