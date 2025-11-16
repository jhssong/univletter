package com.jhssong.univletter.domain.subscribe.entity;

import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.subscribeBoard.entity.SubscribeBoard;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Subscribe extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribe_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime unsubscribedAt;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscribeBoard> subscribeBoards;

    @Builder
    protected Subscribe(String email, Board subscribeBoard) {
        this.email = email;
        this.subscribeBoards = new ArrayList<>();
        this.token = UUID.randomUUID().toString();
    }

    public void resubscribe() {
        this.unsubscribedAt = null;
    }

    public void unsubscribe() {
        this.unsubscribedAt = LocalDateTime.now();
    }
}
