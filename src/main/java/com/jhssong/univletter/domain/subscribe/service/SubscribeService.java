package com.jhssong.univletter.domain.subscribe.service;

import static com.jhssong.univletter.domain.subscribe.exception.SubscribeExceptionUtils.SubscriptionNotFound;

import com.jhssong.univletter.domain.subscribe.dto.SubscribeDelReqDTO;
import com.jhssong.univletter.domain.subscribe.dto.SubscribeReqDTO;
import com.jhssong.univletter.domain.subscribe.entity.Subscribe;
import com.jhssong.univletter.domain.subscribe.repository.SubscribeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        } else {
            subscribe = Subscribe.create(reqDTO);
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
        } else {
            throw SubscriptionNotFound();
        }
    }

}
