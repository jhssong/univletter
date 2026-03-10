package com.jhssong.univletter.domain.subscribe.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record SubscribeDetailResDTO(
        @NotNull(message = "구독자 ID")
        Long id,
        @NotNull(message = "구독중인 게시판")
        List<Long> boardIds
) {
        public static SubscribeDetailResDTO from(Long subscribeId, List<Long> boardIds) {
                return SubscribeDetailResDTO.builder()
                        .id(subscribeId)
                        .boardIds(boardIds)
                        .build();
        }

}
