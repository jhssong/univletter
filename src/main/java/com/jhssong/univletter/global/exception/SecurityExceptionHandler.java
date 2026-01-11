package com.jhssong.univletter.global.exception;

import static org.slf4j.LoggerFactory.getLogger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhssong.errorping.ErrorpingService;
import com.jhssong.errorping.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityExceptionHandler {
    private static final Logger EXCEPTION_DETAIL_LOGGER = getLogger("ERROR_DETAIL_LOGGER");
    private final ErrorpingService errorpingService;

    private String convertErrorResponseToMap(ErrorResponse er) throws JsonProcessingException {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("title", er.getTitle());
        body.put("status", er.getStatus());
        body.put("message", er.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(body);
    }

    private void logDetailedException(Exception ex) {
        StackTraceElement[] origin = ex.getStackTrace();
        EXCEPTION_DETAIL_LOGGER.error(
                "Exception occurred at {}.{}({}:{}): {}",
                origin[0].getClassName(),
                origin[0].getMethodName(),
                origin[0].getFileName(),
                origin[0].getLineNumber(),
                ex.getMessage(),
                ex
        );
    }

    public void handle(CustomException ex,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ex.getStatus())
                .title(ex.getTitle())
                .message(ex.getMessage())
                .build();
        if (ex.getStatus().is5xxServerError()) {
            log.error("[CustomException] status={} method={} uri={} title={} message={}",
                    ex.getStatus().value(),
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getTitle(),
                    ex.getMessage());
            logDetailedException(ex);
            errorpingService.sendErrorToDiscord(errorResponse, request);
        } else {
            log.warn("[CustomException] status={} method={} uri={} title={} message={}",
                    ex.getStatus().value(),
                    request.getMethod(),
                    request.getRequestURI(),
                    ex.getTitle(),
                    ex.getMessage());
        }

        SecurityContextHolder.clearContext();
        response.setStatus(errorResponse.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(convertErrorResponseToMap(errorResponse));
    }
}
