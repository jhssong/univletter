package com.jhssong.univletter.domain.article.service;

import static com.jhssong.univletter.domain.article.exception.ArticleExceptionUtils.NoticeAlreadyExists;

import com.jhssong.univletter.domain.article.dto.ArticleDTO;
import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.article.repository.ArticleRepository;
import com.jhssong.univletter.domain.board.BoardExceptionUtils;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void addNotice(ArticleDTO reqDTO) {
        Board board = boardRepository.findByNameAndSubName(reqDTO.boardName(), reqDTO.boardSubName()).orElseThrow(
                BoardExceptionUtils::BoardNotFound
        );
        Optional<Article> existing = articleRepository.findByTitleAndWrittenAt(reqDTO.title(), reqDTO.writtenAt());

        if (existing.isPresent()) {
            throw NoticeAlreadyExists();
        } else {
            Article article = Article.create(reqDTO, board);
            articleRepository.save(article);
            log.info("새로운 공지글이 추가되었습니다. (게시판: {}, 분야: {}, 제목: {})", board.getName(), board.getSubName(),
                    article.getTitle());
        }
    }
}
