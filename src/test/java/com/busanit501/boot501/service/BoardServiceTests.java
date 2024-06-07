package com.busanit501.boot501.service;

import com.busanit501.boot501.dto.BoardDTO;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired
    BoardService boardService;

    @Test
    public void testInsert(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("내일 모하니?")
                .content("부모님 인사하기")
                .writer("이상용")
                .build();
        Long bno =boardService.register(boardDTO);
        log.info("추가 후에 반환 게시글 번호 bno : " + bno);
    }

    @Test
    public void testRead(){
        BoardDTO boardDTO =boardService.read(301L);
        log.info("하나 조회 boardDTO : " + boardDTO);
    }

    @Test
    public void testUpdate(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(301L)
                .title("내일 모하니? 수정 ver")
                .content("부모님 인사하기 수정 ver")
                .build();
        boardService.update(boardDTO);
    }

    @Test
    public void testdelete(){
        boardService.delete(301L);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("twc")
                .keyword("오늘")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> pageResponseDTO =boardService.list(pageRequestDTO);
        log.info("List 테스트 pageResponseDTO : " + pageResponseDTO);
    }
}
