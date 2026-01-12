package com.jhssong.univletter.global.mail;

import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.article.service.ArticleService;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.repository.SubscribeRepository;
import com.jhssong.univletter.domain.subscribeBoard.entity.SubscribeBoard;
import com.jhssong.univletter.domain.subscribeBoard.repository.SubscribeBoardRepository;
import com.jhssong.univletter.global.mail.exception.EmailExceptionUtils;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final SubscribeRepository subscribeRepository;
    private final SubscribeBoardRepository subscribeBoardRepository;

    private final ArticleService articleService;

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendDailyNewsletter(int timeWindow) {
        List<Subscribe> subscribes = subscribeRepository.findAll();

        for (Subscribe subscribe : subscribes) {
            List<SubscribeBoard> subscribeBoards = subscribeBoardRepository.findAllBySubscribeWithBoard(subscribe);

            // Get article by Boards
            List<ArticleResDTO> articles = new ArrayList<>();
            for (SubscribeBoard subscribeBoard : subscribeBoards) {
                List<ArticleResDTO> article = articleService.fetchArticlesForBoard(subscribeBoard.getBoard(),
                        timeWindow);
                articles.addAll(article);
            }

            if (articles.isEmpty()) {
                continue;
            }
            articles.sort(Comparator.comparing(ArticleResDTO::writtenAt));

            log.info("이메일({})로 {}개의 공지사항을 전송합니다.", subscribe.getEmail(), articles.size());

            Context context = buildNewsletterContext(articles, subscribe);
            sendNewsletterToSubscriber(subscribe, articles.size(), context);
        }

        log.info("이메일 전송 스케줄러 종료");
    }

    private Context buildNewsletterContext(List<ArticleResDTO> articles, Subscribe subscriber) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
        String unsubscribeURL = "https://univletter.jhssong.com/unsubscribe/";

        Context context = new Context();
        context.setVariable("mainTitle", todayDate + " 공지사항");
        context.setVariable("articles", articles);
        context.setVariable("unsubscribeLink", unsubscribeURL + subscriber.getToken());
        return context;
    }

    private void sendNewsletterToSubscriber(Subscribe subscriber, int articleSize, Context context) {
        try {
            String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
            sendEmailWithHtmlTemplate(
                    subscriber.getEmail(),
                    "[UnivLetter] " + articleSize + "개의 새로운 공지사항",
                    context
            );
            log.info("이메일이 성공적으로 전송되었습니다. (to: {})", subscriber.getEmail());
        } catch (AuthenticationFailedException e) {
            throw EmailExceptionUtils.AuthenticationFailedException(subscriber.getEmail(), e.getMessage());
        } catch (MessagingException e) {
            throw EmailExceptionUtils.MessagingException(subscriber.getEmail(), e.getMessage());
        } catch (Exception e) {
            throw EmailExceptionUtils.Exception(subscriber.getEmail(), e.getMessage());
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
            String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM월 dd일"));
            sendEmailWithHtmlTemplate(toEmail, "[UnivLetter] 이메일 전송 테스트 " + todayDate, new Context());
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
