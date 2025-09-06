package com.jhssong.univletter.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class StartupRunner implements ApplicationRunner {

    private final ErrorpingService errorpingService;

    @Override
    public void run(ApplicationArguments args) {
        initializeOnce();
    }

    private void initializeOnce() {
        errorpingService.sendInfo("Univletter 서버가 실행되었습니다.");
    }
}

