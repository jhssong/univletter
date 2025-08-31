package com.jhssong.univletter.domain.board.controller;

import com.jhssong.univletter.domain.board.dto.BoardResDTO;
import com.jhssong.univletter.domain.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/board/all")
    public ResponseEntity<List<BoardResDTO>> getAllBoards() {
        List<BoardResDTO> boardResDTOS = boardService.getAllBoards();
        return ResponseEntity.ok(boardResDTOS);
    }

    @GetMapping("/api/board/names")
    public ResponseEntity<List<String>> getAllBoardNames() {
        List<String> boardNames = boardService.getAllBoardNames();
        return ResponseEntity.ok(boardNames);
    }
}
