package com.jhssong.univletter.domain.article.dto;

import com.jhssong.univletter.domain.article.entity.Article;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        return ArticleResDTO.builder()
                .title(article.getTitle())
                .link(article.getLink())
                .views(article.getViews())
                .author(article.getAuthor())
                .writtenAt(article.getWrittenAt())
                .boardName(article.getBoard().getName())
                .boardSubName(article.getBoard().getSubName())
                .build();
    }

    public static List<ArticleResDTO> fromEntity(List<Article> articles) {
        return articles.stream()
                .map(ArticleResDTO::fromEntity)
                .toList();
    }

    public String getFormattedDate() {
        return this.writtenAt != null ? this.writtenAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}