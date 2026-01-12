package com.jhssong.univletter.global.auth.service;

import com.jhssong.univletter.global.auth.exception.AuthExceptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {
    private final AdminProperties adminProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // Session 기반 인증
        Boolean isAdmin =
                (Boolean) request.getSession().getAttribute("ADMIN");
        if (Boolean.TRUE.equals(isAdmin)) {
            return true;
        }

        // API Key 기반 인증
        String apiKey = request.getHeader("X-ADMIN-KEY");
        if (adminProperties.getApiKey().equals(apiKey)) {
            return true;
        }

        throw AuthExceptionUtils.Unauthorized();
    }
}
