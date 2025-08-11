package com.jhssong.univletter.domain.board.entity;

import com.jhssong.univletter.domain.board.dto.BoardJsonDTO;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subName;

    @Column(nullable = false)
    private String link;

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
