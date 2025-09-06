package com.jhssong.univletter.global.mail;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.board.service.BoardService;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import com.jhssong.univletter.global.mail.exception.EmailExceptionUtils;
import jakarta.mail.AuthenticationFailedException;
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

    public void sendDailyNewsletter(int timeWindow) {
        List<SubscribeResDTO> subscribers = subscribeService.getAllSubscribers();
        List<String> boardNames = boardService.getAllBoardNames();

        for (String boardName : boardNames) {
            List<ArticleResDTO> articles = fetchArticlesForBoard(boardName, timeWindow);

            if (articles.isEmpty()) {
                log.info("전송할 공지사항이 없어 이메일을 전송하지 않습니다. ({})", boardName);
                continue;
            }

            log.info("총 {}개의 공지사항을 전송합니다. ({})", articles.size(), boardName);

            for (SubscribeResDTO subscriber : subscribers) {
                Context context = buildNewsletterContext(articles, subscriber);
                sendNewsletterToSubscriber(subscriber, boardName, context);
            }
        }

        log.info("이메일 전송 스케줄러 종료");
    }

    private List<ArticleResDTO> fetchArticlesForBoard(String boardName, int timeWindow) {
        return boardService.getBoardWithArticlesByBoardName(boardName, timeWindow).stream()
                .flatMap(boardWithArticleResDTO -> boardWithArticleResDTO.articleResDTOS().stream())
                .toList();
    }

    private Context buildNewsletterContext(List<ArticleResDTO> articles, SubscribeResDTO subscriber) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
        String unsubscribeURL = "https://univletter.jhssong.com/unsubscribe/";

        Context context = new Context();
        context.setVariable("mainTitle", todayDate + " 공지사항");
        context.setVariable("articles", articles);
        context.setVariable("unsubscribeLink", unsubscribeURL + subscriber.token());
        return context;
    }

    private void sendNewsletterToSubscriber(SubscribeResDTO subscriber, String boardName, Context context) {
        try {
            sendEmailWithHtmlTemplate(
                    subscriber.email(),
                    "[UnivLetter] " + boardName + " 공지사항",
                    context
            );
            log.info("이메일이 성공적으로 전송되었습니다. (to: {})", subscriber.email());
        } catch (AuthenticationFailedException e) {
            throw EmailExceptionUtils.AuthenticationFailedException(subscriber.email(), e.getMessage());
        } catch (MessagingException e) {
            throw EmailExceptionUtils.MessagingException(subscriber.email(), e.getMessage());
        } catch (Exception e) {
            throw EmailExceptionUtils.Exception(subscriber.email(), e.getMessage());
        }

    }

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

    public void testSendingEmail(String toEmail) {
        try {
            sendEmailWithHtmlTemplate(toEmail, "[UnivLetter] 이메일 전송 테스트", new Context());
            log.info("테스트 이메일이 성공적으로 전송되었습니다.");
        } catch (AuthenticationFailedException e) {
            throw EmailExceptionUtils.AuthenticationFailedException(toEmail, e.getMessage());
        } catch (MessagingException e) {
            throw EmailExceptionUtils.MessagingException(toEmail, e.getMessage());
        } catch (Exception e) {
            throw EmailExceptionUtils.Exception(toEmail, e.getMessage());
        }
    }
}
