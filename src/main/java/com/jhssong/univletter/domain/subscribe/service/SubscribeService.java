package com.jhssong.univletter.domain.subscribe.service;

import static com.jhssong.univletter.domain.subscribe.exception.SubscribeExceptionUtils.SubscriptionNotFound;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeDelReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.repository.SubscribeRepository;
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

    @Transactional
    public Subscribe subscribe(SubscribeReqDTO reqDTO) {
        Optional<Subscribe> existing = subscribeRepository.findByEmail(reqDTO.email());

        Subscribe subscribe;
        if (existing.isPresent()) {
            subscribe = existing.get();
            subscribe.update(reqDTO);
            log.info("구독자 정보가 수정되었습니다. (이메일: {}, 이름: {})", reqDTO.email(), reqDTO.name());
        } else {
            subscribe = Subscribe.create(reqDTO);
            log.info("구독자 정보가 추가되었습니다. (이메일: {}, 이름: {})", reqDTO.email(), reqDTO.name());
        }
        return subscribeRepository.save(subscribe);
    }

    @Transactional
    public void unsubscribe(SubscribeDelReqDTO reqDTO) {
        Optional<Subscribe> existing = subscribeRepository.findByEmailAndTokenAndUnsubscribedAtIsNull(reqDTO.email(),
                reqDTO.token());
        Subscribe subscribe;
        if (existing.isPresent()) {
            subscribe = existing.get();
            subscribe.unsubscribe();
            log.info("{}님이 구독을 취소하였습니다. (이메일: {})", subscribe.getName(), subscribe.getEmail());
        } else {
            throw SubscriptionNotFound();
        }
    }

}
