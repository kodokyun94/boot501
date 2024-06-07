package com.busanit501.boot501.repository;

import com.busanit501.boot501.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
           Board board = Board.builder()
                   .title("오늘 점심 뭐먹지?" + i)
                   .content("한식" + i)
                   .writer("이상용"+(i%10))
                   .build();
           Board result = boardRepository.save(board);
           log.info("추가한 BNO"+result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno=100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info("조회 결과 : "+board);
    }

    @Test
    public void testUpdate(){
        Long bno=100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info("조회 결과1 : "+board);
        board.changeTitleAndContent("오늘 점심은 이거닷 수정버전","로제떡볶이, 냉라면, 족발");
        //반영
        boardRepository.save(board);
        log.info("조회 결과2 : "+board);
    }

    @Test
    public void testDelete(){
        Long bno=100L;
        //반영
        boardRepository.deleteById(bno);
        log.info("조회 결과2 후, 디비에서 삭제 여부 확인 ");
    }

    @Test
    public void testPaging(){
       Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
       Page<Board> result =boardRepository.findAll(pageable);

       log.info("전체 갯수 total :" + result.getTotalElements());
       log.info("전체 페이지  :" + result.getTotalPages());
       log.info("전체 페이지 NO :" + result.getNumber());
       log.info("전체 사이즈 total :" + result.getSize());
       log.info("전체 불러올 데이터 목록 :" );

       List<Board> list =result.getContent();
       list.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch(){
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
        boardRepository.search(pageable);
    }

    @Test
    public void testSearchAll(){
        //검색 조건 더미 데이터
        String[] types = {"t","w","c"};
        //키워드
        String keyword = "점심";

        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());

        Page<Board> result =boardRepository.searchAll(types,keyword,pageable);

        log.info("Querydsl 결과 : 전체 갯수 total :" + result.getTotalElements());
        log.info("Querydsl 결과 : 전체 페이지  :" + result.getTotalPages());
        log.info("Querydsl 결과 : 전체 페이지 NO :" + result.getNumber());
        log.info("Querydsl 결과 : 전체 사이즈 total :" + result.getSize());
        log.info("Querydsl 결과 : 전체 불러올 데이터 목록 :");
        log.info("Querydsl 결과 : 이전 페이지  존재 여부  result.hasPrevious() : " + result.hasPrevious());
        log.info("Querydsl 결과 : 이후 페이지  존재 여부  result.hasNext() : " + result.hasNext());
        List<Board> list =result.getContent();
        list.forEach(board -> log.info(board));
    }

}//끝
