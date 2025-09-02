package com.jhssong.univletter.domain.article.controller;

import com.jhssong.univletter.domain.article.service.ArticleCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleCrawler articleCrawler;

    @PostMapping("/api/admin/testCrawling")
    public ResponseEntity<Void> testCrawling() {
        articleCrawler.crawl();
        return ResponseEntity.ok().build();
    }
}
