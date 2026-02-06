package com.jhssong.univletter.global.schedule;

import com.jhssong.univletter.domain.article.service.ArticleCrawler;
import com.jhssong.univletter.domain.article.service.ArticleService;
import com.jhssong.univletter.global.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private static final int timeWindow = 1;

    private final ArticleCrawler articleCrawler;
    private final EmailService emailService;
    private final ArticleService articleService;

    @Scheduled(cron = "0 0 6 * * *")
    public void crawlAndSendNewsletter() {
        // 1. Crawling articles
        articleCrawler.crawl(timeWindow);

        // 2. Sending mail
        emailService.sendDailyNewsletter(timeWindow);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteArticleOver7days() {
        articleService.deleteArticleOver7days();
    }

}
