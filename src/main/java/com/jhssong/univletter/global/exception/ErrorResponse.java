package com.jhssong.univletter.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

@Builder
public record ErrorResponse(
        String title,
        int status,
        String detail,
        String instance,
        LocalDateTime timestamp
) {
    public static ResponseEntity<ErrorResponse> toResponseEntity(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .detail("관리자에게 문의해주세요.")
                        .instance(request.getDescription(false).replace("uri=", ""))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(BaseDomainException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorResponse.builder()
                        .title(ex.getStatus().getReasonPhrase())
                        .status(ex.getStatus().value())
                        .detail(ex.getMessage())
                        .instance(request.getDescription(false).replace("uri=", ""))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(MethodArgumentNotValidException ex,
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail(message)
                        .instance(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpRequestMethodNotSupportedException ex,
                                                                 HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.builder()
                        .title(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                        .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .detail("지원되지 않는 요청 메서드입니다.")
                        .instance(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
