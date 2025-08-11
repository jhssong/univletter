package com.jhssong.univletter.domain.notice.repository;

import com.jhssong.univletter.domain.notice.entity.Notice;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByTitleAndWrittenAt(String title, LocalDate writtenAt);
}
