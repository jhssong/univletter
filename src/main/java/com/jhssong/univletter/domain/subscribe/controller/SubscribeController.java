package com.jhssong.univletter.domain.subscribe.controller;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeDelReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeSuccessResDTO;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe")
    public ResponseEntity<SubscribeSuccessResDTO> subscribe(@Valid @RequestBody SubscribeReqDTO reqDTO) {
        Subscribe subscribe = subscribeService.subscribe(reqDTO);
        return ResponseEntity.ok(SubscribeSuccessResDTO.fromEntity(subscribe));
    }

    @GetMapping("/api/admin/subscribe/all")
    public ResponseEntity<List<SubscribeResDTO>> getAllSubscribers() {
        List<SubscribeResDTO> subscribeResDTOS = subscribeService.getAllSubscribers();
        return ResponseEntity.ok(subscribeResDTOS);
    }

    @DeleteMapping("/api/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@Valid @RequestBody SubscribeDelReqDTO reqDTO) {
        subscribeService.unsubscribe(reqDTO);
        return ResponseEntity.ok().build();
    }


}
