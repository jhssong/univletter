package com.jhssong.univletter.domain.subscribe.dto;

import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SubscribeResDTO(
        @NotNull
        Long id,
        @NotBlank(message = "이메일")
        String email,
        @NotBlank(message = "토큰")
        String token,
        @NotNull(message = "구독 여부")
        boolean isSubscribed,
        @NotNull(message = "구독일")
        LocalDate subscribedDate,
        @NotNull(message = "구독해지일")
        LocalDate unsubscribedDate
) {
    public static SubscribeResDTO fromEntity(Subscribe subscribe) {
        return SubscribeResDTO.builder()
                .id(subscribe.getId())
                .email(subscribe.getEmail())
                .token(subscribe.getToken())
                .isSubscribed(subscribe.getUnsubscribedAt() == null)
                .subscribedDate(subscribe.getCreatedAt().toLocalDate())
                .unsubscribedDate(
                        subscribe.getUnsubscribedAt() == null ? null : subscribe.getUnsubscribedAt().toLocalDate())
                .build();
    }
}
