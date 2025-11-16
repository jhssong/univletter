package com.jhssong.univletter.domain.subscribe.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SubscribeReqDTO(
        @NotBlank(message = "이메일")
        @Email
        String email,
        @NotNull(message = "게시판 ID")
        List<Long> boardIds
) {
}
