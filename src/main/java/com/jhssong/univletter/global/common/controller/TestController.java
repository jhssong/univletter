package com.jhssong.univletter.global.common.controller;

import com.jhssong.univletter.domain.article.service.ArticleCrawler;
import com.jhssong.univletter.domain.article.service.ArticleService;
import com.jhssong.univletter.global.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final ArticleCrawler articleCrawler;
    private final EmailService emailService;
    private final ArticleService articleService;

    @PostMapping("/testCrawling")
    public ResponseEntity<Void> testCrawling(@RequestParam int timeWindow) {
        articleCrawler.crawl(timeWindow);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/testSendingEmail")
    public ResponseEntity<Void> testSendingEmail(@RequestParam String toEmail) {
        emailService.testSendingEmail(toEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendDailyNewsletterManually")
    public ResponseEntity<Void> sendDailyNewsletterManually(@RequestParam int timeWindow) {
        emailService.sendDailyNewsletter(timeWindow);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/testDelete")
    public ResponseEntity<Void> deleteArticleOver7days() {
        articleService.deleteArticleOver7days();
        return ResponseEntity.ok().build();
    }

}
