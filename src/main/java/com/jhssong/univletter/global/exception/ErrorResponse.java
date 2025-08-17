package com.jhssong.univletter.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@Builder
public record ErrorResponse(
        String title,
        int status,
        String detail,
        String instance,
        String method,
        LocalDateTime timestamp
) {
    private static String getMethodFromRequest(WebRequest request) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest httpServletRequest = attributes.getRequest();
            return httpServletRequest.getMethod();
        }
        return "N/A";
    }

    public static ErrorResponse toResponseEntity(BaseDomainException ex, WebRequest request) {
        log.error("에러 발생! {}-{}", ex.getStatus().value(), ex.getMessage());
        return ErrorResponse.builder()
                .title(ex.getStatus().getReasonPhrase())
                .status(ex.getStatus().value())
                .detail(ex.getMessage())
                .instance(request.getDescription(false).replace("uri=", ""))
                .method(getMethodFromRequest(request))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse toResponseEntity(MethodArgumentNotValidException ex,
                                                 HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    String field = error.getField();
                    String defaultMessage = error.getDefaultMessage();
                    String failedConstraint = "";

                    // error.getCodes() returns an array like ["DTO.email.NotBlank", "NotBlank", ...]
                    // The last element usually represents the validation constraint name
                    String[] codes = error.getCodes();
                    if (codes != null && codes.length > 0) {
                        failedConstraint = codes[codes.length - 1];
                    }

                    return switch (failedConstraint) {
                        case "NotNull" -> field + "은(는) 필수 입력 항목입니다.";
                        case "NotBlank" -> field + "은(는) 빈 값 또는 공백일 수 없습니다.";
                        case "Email" -> "이메일 형식이 올바르지 않습니다.";
                        default -> field + " " + defaultMessage;
                    };
                })
                .distinct()
                .collect(Collectors.joining(", "));
        log.error("사용자 입력 에러 발생! {}-{}", HttpStatus.BAD_REQUEST.value(), message);
        return ErrorResponse.builder()
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(message)
                .instance(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse toResponseEntity(HttpRequestMethodNotSupportedException ex,
                                                 HttpServletRequest request) {
        return ErrorResponse.builder()
                .title(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .detail("지원되지 않는 요청 메서드입니다.")
                .instance(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse toResponseEntity(NoResourceFoundException ex,
                                                 HttpServletRequest request) {
        return ErrorResponse.builder()
                .title(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .detail("지원되지 않는 요청 메서드입니다.")
                .instance(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse toResponseEntity(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ErrorResponse.builder()
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail("관리자에게 문의해주세요.")
                .instance(request.getDescription(false).replace("uri=", ""))
                .method(getMethodFromRequest(request))
                .timestamp(LocalDateTime.now())
                .build();
    }


}
