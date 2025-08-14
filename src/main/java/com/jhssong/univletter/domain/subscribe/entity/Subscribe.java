package com.jhssong.univletter.domain.subscribe.entity;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime unsubscribedAt;

    @Builder
    protected Subscribe(String email) {
        this.email = email;
        this.token = UUID.randomUUID().toString();
    }

    public static Subscribe create(SubscribeReqDTO reqDTO) {
        return Subscribe.builder()
                .email(reqDTO.email())
                .build();
    }

    public void update(SubscribeReqDTO reqDTO) {
        this.unsubscribedAt = null;
    }

    public void unsubscribe() {
        this.unsubscribedAt = LocalDateTime.now();
    }
}
