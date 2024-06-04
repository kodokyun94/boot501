package com.busanit501.boot501.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//데이터 만 전달
@RestController
@Log4j2
public class RestSampleController {
    @GetMapping("/hiRest")
    public String[] hi(){
        log.info("hi test~~~~~~rest");
        return new String[]{"오늘 점심 뭐먹지?","도시락","김치볶음밥"};
    }
}
