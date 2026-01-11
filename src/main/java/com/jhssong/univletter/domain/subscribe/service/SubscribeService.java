package com.jhssong.univletter.domain.subscribe.service;

import static com.jhssong.univletter.domain.board.exception.BoardExceptionUtils.BoardNotFound;
import static com.jhssong.univletter.domain.subscribe.exception.SubscribeExceptionUtils.SubscriptionNotFound;

import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeDelReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeResDTO;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.repository.SubscribeRepository;
import com.jhssong.univletter.domain.subscribeBoard.entity.SubscribeBoard;
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
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Subscribe subscribe(SubscribeReqDTO reqDTO) {
        Optional<Subscribe> existing = subscribeRepository.findByEmail(reqDTO.email());
        Subscribe subscribe;
        if (existing.isPresent()) {
            subscribe = existing.get();
            if (subscribe.getUnsubscribedAt() != null) {
                subscribe.resubscribe();
                log.info("이메일({})가 구독을 다시 시작했습니다.", reqDTO.email());
            }
        } else {
            subscribe = Subscribe.builder()
                    .email(reqDTO.email())
                    .build();
            log.info("이메일({})가 구독을 시작했습니다.", reqDTO.email());
        }

        // 게시판 별 구독 설정
        for (Long boardId : reqDTO.boardIds()) {
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> BoardNotFound("N/A", "N/A"));

            boolean alreadyLinked = subscribe.getSubscribeBoards().stream()
                    .anyMatch(sb -> sb.getBoard().getId().equals(boardId));

            if (!alreadyLinked) {
                SubscribeBoard subscribeBoard = SubscribeBoard.builder()
                        .subscribe(subscribe)
                        .board(board)
                        .build();
                subscribe.getSubscribeBoards().add(subscribeBoard);
                log.info("이메일({})가 '{}({})' 게시판을 구독했습니다.", reqDTO.email(), board.getName(), board.getSubName());
            }
        }

        return subscribeRepository.save(subscribe);
    }

    @Transactional
    public void unsubscribe(SubscribeDelReqDTO reqDTO) {
        Optional<Subscribe> existing = subscribeRepository.findByTokenAndUnsubscribedAtIsNull(reqDTO.token());
        Subscribe subscribe;
        if (existing.isPresent()) {
            subscribe = existing.get();
            subscribe.unsubscribe();
            log.info("이메일({})가 구독을 취소했습니다.", subscribe.getEmail());
        } else {
            throw SubscriptionNotFound();
        }
    }

    public List<SubscribeResDTO> getAllSubscribers() {
        List<Subscribe> subscribers = subscribeRepository.findAll();
        return subscribers.stream().map(SubscribeResDTO::fromEntity).toList();
    }

}
