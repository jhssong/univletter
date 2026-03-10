package com.jhssong.univletter.domain.article.entity;

import com.jhssong.univletter.domain.article.dto.ArticleReqDTO;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String link;

    private int views;

    private String author;

    @Column(nullable = false)
    private LocalDate writtenAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Article(String title, String link, int views, String author, LocalDate writtenAt, Board board) {
        this.title = title;
        this.link = link;
        this.views = views;
        this.author = author;
        this.writtenAt = writtenAt;
        this.board = board;
    }

    public static Article create(ArticleReqDTO dto, Board board) {
        return Article.builder()
                .title(dto.title())
                .link(dto.link())
                .views(dto.views())
                .author(dto.author())
                .writtenAt(dto.writtenAt())
                .board(board)
                .build();
    }

}
