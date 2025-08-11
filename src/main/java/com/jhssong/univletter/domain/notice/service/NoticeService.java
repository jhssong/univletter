package com.jhssong.univletter.domain.notice.service;

import static com.jhssong.univletter.domain.notice.NoticeExceptionUtils.NoticeAlreadyExists;

import com.jhssong.univletter.domain.board.BoardExceptionUtils;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import com.jhssong.univletter.domain.notice.dto.NoticeDTO;
import com.jhssong.univletter.domain.notice.entity.Notice;
import com.jhssong.univletter.domain.notice.repository.NoticeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Notice addNotice(NoticeDTO reqDTO) {
        Board board = boardRepository.findByNameAndSubName(reqDTO.boardName(), reqDTO.boardSubName()).orElseThrow(
                BoardExceptionUtils::BoardNotFound
        );
        Optional<Notice> existing = noticeRepository.findByTitleAndWrittenAt(reqDTO.title(), reqDTO.writtenAt());

        if (existing.isPresent()) {
            throw NoticeAlreadyExists();
        } else {
            Notice notice = Notice.create(reqDTO, board);
            return noticeRepository.save(notice);
        }
    }
}
