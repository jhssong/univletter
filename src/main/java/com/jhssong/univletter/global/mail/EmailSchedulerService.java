package com.jhssong.univletter.global.mail;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.board.dto.BoardResDTO;
import com.jhssong.univletter.domain.board.service.BoardService;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import jakarta.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSchedulerService {

    private final EmailService emailService;
    private final BoardService boardService;
    private final SubscribeService subscribeService;

    @Scheduled(cron = "0 0 7 * * *")
    public void sendDailyArticle() {
        log.info("이메일 전송 스케줄러 시작 - 현재 시간: {}", LocalDateTime.now());

        Context context = new Context();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
        context.setVariable("mainTitle", todayDate + " 공지사항");

        List<SubscribeResDTO> subscribeResDTOS = subscribeService.getAllSubscribers();
        List<String> boardNames = boardService.getAllBoardNames();

        for (String boardName : boardNames) {
            // TODO Categorize subscribers according to their subscribed boards
            List<BoardResDTO> boardResDTOS = boardService.getBoardWithArticlesByBoardName(boardName);

            List<ArticleResDTO> allArticlesForNewsletter = boardResDTOS.stream()
                    .flatMap(boardResDTO -> boardResDTO.articleResDTOS().stream())
                    .toList();

            context.setVariable("articles", allArticlesForNewsletter);

            if (allArticlesForNewsletter.isEmpty()) {
                log.info("전송할 공지사항이 없어 이메일을 전송하지 않습니다. ({})", boardName);
                return;
            } else {
                log.info("총 {}개의 공지사항을 전송합니다. ({})", allArticlesForNewsletter.size(), boardName);
            }

            context.setVariable("unsubscribeLink", "https://univletter.jhssong.com/unsubscribe");

            for (SubscribeResDTO subscribeResDTO : subscribeResDTOS) {
                try {
                    emailService.sendEmailWithHtmlTemplate(
                            subscribeResDTO.email(),
                            "[UnivLetter] " + boardName + " 공지사항",
                            "newsletter-template",
                            context
                    );
                    log.info("이메일이 성공적으로 전송되었습니다.");
                } catch (MessagingException e) {
                    log.error("이메일 전송 실패: {}", e.getMessage(), e);
                } catch (Exception e) {
                    log.error("이메일 전송 테스트 중 오류 발생: {}", e.getMessage(), e);
                }

            }
        }

        log.info("이메일 전송 스케줄러 종료");
    }
}
