package com.jhssong.univletter.global.common.controller;

import com.jhssong.univletter.domain.article.service.ArticleCrawler;
import com.jhssong.univletter.global.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final ArticleCrawler articleCrawler;
    private final EmailService emailService;

    @PostMapping("/sendDailyNewsletterManually")
    public ResponseEntity<Void> sendDailyNewsletterManually() {
        emailService.sendDailyNewsletter();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/testCrawlAndSendNewsletter")
    public ResponseEntity<Void> testCrawlAndSendNewsletter() {
        articleCrawler.crawl();
        emailService.sendDailyNewsletter();
        return ResponseEntity.ok().build();
    }
}
