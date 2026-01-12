package com.jhssong.univletter.global.auth.controller;

import com.jhssong.univletter.global.auth.dto.LoginReqDTO;
import com.jhssong.univletter.global.auth.exception.AuthExceptionUtils;
import com.jhssong.univletter.global.auth.service.AdminProperties;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AuthController {
    private final AdminProperties adminProperties;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginReqDTO req, HttpSession session) {
        if (adminProperties.getEmail().equals(req.email()) && adminProperties.getPassword().equals(req.password())) {
            session.setAttribute("ADMIN", true);
            session.setMaxInactiveInterval(30 * 60); // 30 min
            log.info("LOGIN SESSION ID = {}", session.getId());

            return ResponseEntity.ok().build();
        }
        throw AuthExceptionUtils.Unauthorized();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Void> me(HttpSession session) {
        if (session == null || session.getAttribute("ADMIN") == null) {
            throw AuthExceptionUtils.Unauthorized();
        }
        return ResponseEntity.ok().build();
    }
}

