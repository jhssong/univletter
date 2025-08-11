package com.jhssong.univletter.domain.notice.controller;

import com.jhssong.univletter.domain.notice.dto.NoticeDTO;
import com.jhssong.univletter.domain.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/api/notice")
    public ResponseEntity<Void> addNotice(@Valid @RequestBody NoticeDTO reqDTO) {
        noticeService.addNotice(reqDTO);
        return ResponseEntity.ok().build();
    }
}
