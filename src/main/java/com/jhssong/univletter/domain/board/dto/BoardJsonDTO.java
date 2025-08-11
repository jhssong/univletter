package com.jhssong.univletter.domain.board.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardJsonDTO(
        @NotBlank(message = "이름")
        String name,
        @NotBlank(message = "분야")
        String subName,
        @NotBlank(message = "링크")
        String link
) {
}
