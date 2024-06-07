package com.busanit501.boot501.repository.search;


import com.busanit501.boot501.domain.Board;
import com.busanit501.boot501.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search(Pageable pageable) {
        //기본문법(sql을 자바형식으로 사용)
        QBoard board = QBoard.board;
        //select from board
        JPQLQuery<Board> query =from(board);

        //BooleanBuilder이용한 조건절 추가
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(board.title.contains("1"));
        booleanBuilder.or(board.content.contains("1"));

        //where 조건절
       // query.where(board.title.contains("1"));

        //booleanBuilder 적용
        query.where(booleanBuilder);
        //bno > 0 적용
        query.where(board.bno.gt(0L));

        //페이징 처리 적용
        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list =query.fetch();
        Long count=query.fetchCount();
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);


        if(types != null && types.length >0 && keyword != null){
            log.info("조건 절 실행 여부 확인");
            BooleanBuilder booleanBuilder= new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L));


        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list =query.fetch();
        Long count=query.fetchCount();

        Page<Board> result = new PageImpl<>(list, pageable, count);

        return result;
    }

}
