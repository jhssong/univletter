package com.jhssong.univletter.domain.article.repository;

import com.jhssong.univletter.domain.article.entity.Article;
import com.jhssong.univletter.domain.board.entity.Board;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByTitleAndWrittenAt(String title, LocalDate writtenAt);

    List<Article> findAllByBoard(Board board);

    void deleteByWrittenAtBefore(LocalDate date);
}
