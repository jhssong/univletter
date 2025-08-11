package com.jhssong.univletter.domain.board.repository;

import com.jhssong.univletter.domain.board.entity.Board;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Collection<Object> findByNameAndSubName(String name, String subName);
}
