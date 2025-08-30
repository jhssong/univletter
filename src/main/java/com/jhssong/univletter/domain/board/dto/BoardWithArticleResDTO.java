package com.jhssong.univletter.domain.board.dto;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record BoardWithArticleResDTO(
        @NotBlank(message = "이름")
        String name,
        @NotBlank(message = "분야")
        String subName,
        @NotBlank(message = "링크")
        String link,
        @NotNull(message = "공지사항 리스트")
        List<ArticleResDTO> articleResDTOS
) {
    public static BoardWithArticleResDTO fromEntity(Board board, List<Article> articles) {
        return BoardWithArticleResDTO.builder()
                .name(board.getName())
                .subName(board.getSubName())
                .link(board.getLink())
                .articleResDTOS(articles.stream()
                        .map(ArticleResDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
