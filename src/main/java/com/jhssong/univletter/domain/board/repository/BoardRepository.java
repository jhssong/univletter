package com.jhssong.univletter.domain.board.repository;

import com.jhssong.univletter.domain.board.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByNameAndSubName(String name, String subName);

    List<Board> findAllByName(String name);

    @Query("SELECT DISTINCT b.name FROM Board b")
    List<String> findDistinctNames();
}
