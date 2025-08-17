package com.jhssong.univletter.global.exception;

import com.jhssong.univletter.global.util.ErrorpingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorpingService errorpingService;

    @ExceptionHandler(BaseDomainException.class)
    public ResponseEntity<ErrorResponse> handleBaseDomainException(BaseDomainException ex, WebRequest request) {
        ErrorResponse res = ErrorResponse.toResponseEntity(ex, request);
        errorpingService.sendError(res);
        return ResponseEntity.status(ex.getStatus())
                .body(res);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorResponse res = ErrorResponse.toResponseEntity(ex, request);
        errorpingService.sendError(res);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(res);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorResponse res = ErrorResponse.toResponseEntity(ex, request);
        errorpingService.sendError(res);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(res);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex,
                                                               HttpServletRequest request) {
        ErrorResponse res = ErrorResponse.toResponseEntity(ex, request);
        errorpingService.sendError(res);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse res = ErrorResponse.toResponseEntity(ex, request);
        errorpingService.sendError(res);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(res);
    }
}
