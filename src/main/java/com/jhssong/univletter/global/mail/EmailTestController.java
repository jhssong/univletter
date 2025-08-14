package com.jhssong.univletter.global.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Profile("local")
public class EmailTestController {

    private final EmailSchedulerService emailSchedulerService;

    @PostMapping("/api/admin/test-send")
    public ResponseEntity<String> sendDailyNewsletterManually() {
        log.info("이메일 전송 테스트 시작.");
        try {
            emailSchedulerService.sendDailyArticle();
            return ResponseEntity.ok("이메일 전송 테스트 완료.");
        } catch (Exception e) {
            log.error("이메일 전송 테스트 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("이메일 전송 테스트 중 오류 발생: " + e.getMessage());
        }
    }
}
