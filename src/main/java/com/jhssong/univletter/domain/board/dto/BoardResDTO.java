package com.jhssong.univletter.domain.board.dto;

import com.jhssong.univletter.domain.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardResDTO(
        @NotNull(message = "게시판 ID")
        Long id,
        @NotBlank(message = "이름")
        String name,
        @NotBlank(message = "분야")
        String subName,
        @NotBlank(message = "링크")
        String link
) {
    public static BoardResDTO fromEntity(Board board) {
        return BoardResDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .subName(board.getSubName())
                .link(board.getLink())
                .build();
    }
}
