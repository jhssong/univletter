package com.jhssong.univletter.domain.board.service;

import com.jhssong.univletter.domain.board.dto.BoardResDTO;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardResDTO::fromEntity).toList();
    }

    public List<String> getAllBoardNames() {
        return boardRepository.findDistinctNames();
    }

}
