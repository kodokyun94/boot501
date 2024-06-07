package com.busanit501.boot501.repository;


import com.busanit501.boot501.domain.Board;
import com.busanit501.boot501.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @Query("select b from Board b where b.title like concat('%',:keyword ,'%')")
    Page<Board> findByKeyword(String keyword, Pageable pageable);

    @Query(value = "select now()",nativeQuery = true)
    String getTime();


}
