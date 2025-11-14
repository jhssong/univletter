package com.jhssong.univletter.domain.subscribe.repository;

import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<Subscribe> findByEmail(String email);

    Optional<Subscribe> findByTokenAndUnsubscribedAtIsNull(String token);
}
