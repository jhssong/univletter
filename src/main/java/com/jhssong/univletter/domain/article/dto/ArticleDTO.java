package com.jhssong.univletter.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ArticleDTO(
        @NotBlank(message = "제목")
        String title,
        @NotBlank(message = "링크")
        String link,
        int views,
        String author,
        @NotNull(message = "작성일")
        LocalDate writtenAt,
        @NotBlank(message = "게시판 이름")
        String boardName,
        @NotBlank(message = "게시판 분류")
        String boardSubName
) {
}