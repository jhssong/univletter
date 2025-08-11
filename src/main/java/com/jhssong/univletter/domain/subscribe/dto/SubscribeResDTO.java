package com.jhssong.univletter.domain.subscribe.dto;

import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SubscribeResDTO(
        @NotBlank(message = "이름")
        String name,
        @NotBlank(message = "이메일")
        String email
) {
    public static SubscribeResDTO fromEntity(Subscribe subscribe) {
        return SubscribeResDTO.builder()
                .name(subscribe.getName())
                .email(subscribe.getEmail())
                .build();
    }
}
