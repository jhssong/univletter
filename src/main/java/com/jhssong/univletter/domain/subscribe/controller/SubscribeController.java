package com.jhssong.univletter.domain.subscribe.controller;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeDelReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.service.SubscribeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe")
    public ResponseEntity<SubscribeResDTO> subscribe(@Valid @ModelAttribute SubscribeReqDTO reqDTO) {
        Subscribe subscribe = subscribeService.subscribe(reqDTO);
        return ResponseEntity.ok(SubscribeResDTO.fromEntity(subscribe));
    }


    @DeleteMapping("/api/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@Valid @ModelAttribute SubscribeDelReqDTO reqDTO) {
        subscribeService.unsubscribe(reqDTO);
        return ResponseEntity.ok().build();
    }

}
