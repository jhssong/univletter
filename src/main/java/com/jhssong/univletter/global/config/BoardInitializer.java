package com.jhssong.univletter.global.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhssong.univletter.domain.board.dto.BoardJsonDTO;
import com.jhssong.univletter.domain.board.entity.Board;
import com.jhssong.univletter.domain.board.repository.BoardRepository;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardInitializer implements CommandLineRunner {

    private final BoardRepository boardRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        InputStream inputStream = new ClassPathResource("boards.json").getInputStream();
        List<BoardJsonDTO> boardData = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        for (BoardJsonDTO jsonDTO : boardData) {
            if (boardRepository.findByNameAndSubName(jsonDTO.name(), jsonDTO.subName()).isEmpty()) {
                Board board = Board.create(jsonDTO);
                boardRepository.save(board);
                log.info("✅ '{}({})' 보드가 성공적으로 추가되었습니다.", jsonDTO.name(), jsonDTO.subName());
            }
        }
    }
}