package com.jhssong.univletter.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jhssong.univletter.domain.article.entity.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record ArticleResDTO(
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
    public static ArticleResDTO fromEntity(Article article) {
        String boardName = article.getBoard().getName();
        String[] boardNameArr = boardName.split(" ");
        if (boardNameArr.length > 1) {
            boardName = boardNameArr[1];
        }
        return ArticleResDTO.builder()
                .title(article.getTitle())
                .link(article.getLink())
                .views(article.getViews())
                .author(article.getAuthor())
                .writtenAt(article.getWrittenAt())
                .boardName(boardName)
                .boardSubName(article.getBoard().getSubName())
                .build();
    }

    @JsonProperty("formattedDate")
    public String getFormattedDate() {
        return this.writtenAt != null ? this.writtenAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}