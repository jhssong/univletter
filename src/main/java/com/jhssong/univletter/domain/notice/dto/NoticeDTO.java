package com.jhssong.univletter.domain.notice.dto;

import com.jhssong.univletter.domain.notice.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record NoticeDTO(
        @NotBlank(message = "제목")
        String title,
        @NotBlank(message = "링크")
        String link,
        int views,
        String author,
        @NotBlank(message = "작성일")
        LocalDate writtenAt,
        @NotBlank(message = "게시판 이름")
        String boardName,
        @NotBlank(message = "게시판 분류")
        String boardSubName
) {
    public static NoticeDTO fromEntity(Notice notice) {
        return NoticeDTO.builder()
                .title(notice.getTitle())
                .link(notice.getLink())
                .views(notice.getViews())
                .author(notice.getAuthor())
                .writtenAt(notice.getWrittenAt())
                .build();
    }
}