package com.jhssong.univletter.domain.article.controller;

import com.jhssong.univletter.domain.article.dto.ArticleDTO;
import com.jhssong.univletter.domain.article.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/notice")
    public ResponseEntity<Void> addNotice(@Valid @RequestBody ArticleDTO reqDTO) {
        articleService.addNotice(reqDTO);
        return ResponseEntity.ok().build();
    }
}
