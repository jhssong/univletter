package com.jhssong.univletter.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class ShutdownListener {

    private final ErrorpingService errorpingService;

    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        errorpingService.sendInfo("Univletter 서버가 종료되었습니다.");
    }
}
