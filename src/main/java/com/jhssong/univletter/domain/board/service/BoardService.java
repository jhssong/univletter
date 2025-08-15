package com.jhssong.univletter.domain.board.service;

import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.board.dto.BoardResDTO;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResDTO> getBoardWithArticlesByBoardName(String boardName) {
        List<Board> boards = boardRepository.findAllByName(boardName);

        LocalDate todayKst = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate oneDayBeforeTodayKst = todayKst.minusDays(1);

        return boards.stream()
                .map(board -> {
                    List<Article> allArticlesForBoard = board.getArticles();

                    // Filtering articles
                    List<Article> filteredArticles = allArticlesForBoard.stream()
                            .filter(article -> article.getWrittenAt() != null && !article.getWrittenAt()
                                    .isBefore(oneDayBeforeTodayKst))
                            .toList();

                    return BoardResDTO.fromEntity(board, filteredArticles);
                })
                .toList();
    }

    public List<String> getAllBoardNames() {
        return boardRepository.findDistinctNames();
    }

    public Page<BoardResDTO> getPageableBoards(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(board -> BoardResDTO.fromEntity(board, List.of()));
    }

}
