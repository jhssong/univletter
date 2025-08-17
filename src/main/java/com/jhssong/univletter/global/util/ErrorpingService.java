package com.jhssong.univletter.global.util;

import com.jhssong.univletter.global.exception.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class ErrorpingService {

    private final WebClient webClient;
    private final String channelId;

    public ErrorpingService(
            @Value("${errorping.apiKey}") String apiKey,
            @Value("${errorping.channelId}") String channelId,
            @Value("${errorping.url}") String url
    ) {
        this.channelId = channelId;
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("x-api-key", apiKey)
                .build();
    }

    public void sendError(ErrorResponse res) {
        Map<String, Object> error = new HashMap<>();
        error.put("title", res.title());
        error.put("status", res.status());
        error.put("detail", res.detail());
        error.put("instance", res.instance());
        error.put("method", res.method());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("channelId", this.channelId);
        requestBody.put("error", error);

        this.webClient.post()
                .uri("")
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        response -> log.debug("Errorping request sent successfully"),
                        throwable -> log.warn("Failed to send error to Errorping: {}", throwable.getMessage())
                );
    }
}
