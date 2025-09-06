package com.jhssong.univletter.domain.article.service;

import com.jhssong.univletter.domain.article.dto.ArticleReqDTO;
import com.jhssong.univletter.domain.article.dto.ArticleResDTO;
import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.article.repository.ArticleRepository;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.exception.BoardExceptionUtils;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import java.util.List;
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
    public void addNotice(ArticleReqDTO reqDTO) {
        Board board = boardRepository.findByNameAndSubName(reqDTO.boardName(), reqDTO.boardSubName()).orElseThrow(
                () -> BoardExceptionUtils.BoardNotFound(reqDTO.boardName(), reqDTO.boardSubName())
        );
        Optional<Article> existing = articleRepository.findByTitleAndWrittenAt(reqDTO.title(), reqDTO.writtenAt());

        if (existing.isEmpty()) {
            Article article = Article.create(reqDTO, board);
            articleRepository.save(article);
            log.info("새로운 공지글이 추가되었습니다. (게시판: {}, 분야: {}, 제목: {})", board.getName(), board.getSubName(),
                    article.getTitle());
        }
    }

    public List<ArticleResDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleResDTO::fromEntity).toList();

    }
}
