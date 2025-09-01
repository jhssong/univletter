package com.jhssong.univletter.global.config.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginResDTO(
        @NotBlank(message = "토큰")
        String accessToken
) {
}
