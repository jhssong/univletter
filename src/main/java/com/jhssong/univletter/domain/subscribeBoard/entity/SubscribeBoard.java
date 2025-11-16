package com.jhssong.univletter.domain.subscribeBoard.entity;

import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SubscribeBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribe_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public SubscribeBoard(Subscribe subscribe, Board board) {
        this.subscribe = subscribe;
        this.board = board;
    }
}
