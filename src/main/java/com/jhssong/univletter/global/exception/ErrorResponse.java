package com.jhssong.univletter.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String joinedFields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(", "));

        String finalMessage = joinedFields + "은(는) 필수 입력값입니다.";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail(finalMessage)
                        .instance(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }
}
