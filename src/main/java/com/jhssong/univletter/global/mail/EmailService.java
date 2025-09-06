package com.jhssong.univletter.global.mail;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.board.dto.BoardWithArticleResDTO;
import com.jhssong.univletter.domain.board.service.BoardService;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final BoardService boardService;
    private final SubscribeService subscribeService;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    private void sendEmailWithHtmlTemplate(String to, String subject, Context context)
            throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        String htmlContent = templateEngine.process("newsletter-template", context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(mimeMessage);
    }

    public void sendDailyNewsletter() {
        Context context = new Context();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
        context.setVariable("mainTitle", todayDate + " 공지사항");

        List<SubscribeResDTO> subscribeResDTOS = subscribeService.getAllSubscribers();
        List<String> boardNames = boardService.getAllBoardNames();

        for (String boardName : boardNames) {
            // TODO Categorize subscribers according to their subscribed boards
            List<BoardWithArticleResDTO> boardWithArticleResDTOS = boardService.getBoardWithArticlesByBoardName(
                    boardName);

            List<ArticleResDTO> allArticlesForNewsletter = boardWithArticleResDTOS.stream()
                    .flatMap(boardWithArticleResDTO -> boardWithArticleResDTO.articleResDTOS().stream())
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
                    sendEmailWithHtmlTemplate(
                            subscribeResDTO.email(),
                            "[UnivLetter] " + boardName + " 공지사항",
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

    public void sendTestEmail(String toEmail) {
        try {
            sendEmailWithHtmlTemplate(toEmail, "[UnivLetter] 이메일 전송 테스트", new Context());
            log.info("테스트 이메일이 성공적으로 전송되었습니다.");
        } catch (MessagingException e) {
            log.error("테스트 이메일 전송 실패: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("테스트 이메일 전송 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}
