package com.jhssong.univletter.domain.board.entity;

import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.board.dto.BoardJsonDTO;
import com.jhssong.univletter.domain.subscribeBoard.entity.SubscribeBoard;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subName;

    @Column(nullable = false)
    private String link;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscribeBoard> subscribeBoards;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @Builder
    public Board(String name, String subName, String link) {
        this.name = name;
        this.subName = subName;
        this.link = link;
    }

    public static Board create(BoardJsonDTO jsonDTO) {
        return Board.builder()
                .name(jsonDTO.name())
                .subName(jsonDTO.subName())
                .link(jsonDTO.link())
                .build();
    }
}
