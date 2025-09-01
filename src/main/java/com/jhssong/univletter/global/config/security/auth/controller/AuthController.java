package com.jhssong.univletter.global.config.security.auth.controller;

import com.jhssong.univletter.global.config.security.auth.dto.LoginReqDTO;
import com.jhssong.univletter.global.config.security.auth.dto.LoginResDTO;
import com.jhssong.univletter.global.config.security.auth.exception.AuthExceptionUtils;
import com.jhssong.univletter.global.config.security.auth.service.AuthUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AuthController {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@Valid @RequestBody LoginReqDTO req) {
        if (adminEmail.equals(req.email()) && adminPassword.equals(req.password())) {
            String accessToken = AuthUtil.createAccessToken(req.email(), SECRET_KEY);
            LoginResDTO loginResDTO = LoginResDTO.builder()
                    .accessToken(accessToken)
                    .build();
            log.info("어드민 로그인 성공");
            return ResponseEntity.ok(loginResDTO);
        }
        log.info("어드민 로그인 실패");
        throw AuthExceptionUtils.Unauthorized();
    }
}