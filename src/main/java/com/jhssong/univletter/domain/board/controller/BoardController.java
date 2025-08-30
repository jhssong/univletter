package com.jhssong.univletter.domain.board.controller;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/api/board")
    public ResponseEntity<List<SubscribeResDTO>> get(@Valid SubscribeReqDTO reqDTO) {

    }
}
