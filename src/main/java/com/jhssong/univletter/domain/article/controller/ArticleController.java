package com.jhssong.univletter.domain.article.controller;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.article.service.ArticleCrawler;
import com.jhssong.univletter.domain.article.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleCrawler articleCrawler;

    @PostMapping("/api/admin/testCrawling")
    public ResponseEntity<Void> testCrawling() {
        articleCrawler.crawl();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/article/all")
    public ResponseEntity<List<ArticleResDTO>> getAllArticles() {
        List<ArticleResDTO> articleResDTOS = articleService.getAllArticles();
        return ResponseEntity.ok(articleResDTOS);
    }
}
