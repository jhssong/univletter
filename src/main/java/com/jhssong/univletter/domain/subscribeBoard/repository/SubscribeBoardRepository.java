package com.jhssong.univletter.domain.subscribeBoard.repository;

import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribeBoard.entity.SubscribeBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeBoardRepository extends JpaRepository<SubscribeBoard, Long> {
    @Query("SELECT sb FROM SubscribeBoard sb JOIN FETCH sb.board WHERE sb.subscribe = :subscribe")
    List<SubscribeBoard> findAllBySubscribeWithBoard(@Param("subscribe") Subscribe subscribe);

}
