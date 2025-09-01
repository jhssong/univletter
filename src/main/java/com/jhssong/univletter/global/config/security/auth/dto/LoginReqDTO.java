package com.jhssong.univletter.global.config.security.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginReqDTO(
        @NotBlank(message = "이메일")
        String email,
        @NotBlank(message = "비밀번호")
        String password
) {
}
