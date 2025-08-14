package com.jhssong.univletter.domain.subscribe.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscribeReqDTO(
        @NotBlank(message = "이메일")
        @Email
        String email
) {
}
