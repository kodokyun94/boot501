package com.busanit501.boot501.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;

    private String keyword;

    private String link;


    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return  type.split("");
    }


    //...props : 가변인자 ex)String title, String content ...등등
    public Pageable getPageable(String... props) {
        // 화면에서 1페이지 -> 0
        // 화면에서 2페이지 -> 1
        return PageRequest.of(this.page - 1, 10, Sort.by(props).descending());
    }


    public String getLink() {
        if (link == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("page=" + this.page);
            stringBuilder.append("&size=" + this.size);

            // 타입, 검색 조건, t,w,c
            if (type != null && type.length() > 0) {
                stringBuilder.append("&type=" + type);
            }

            // 검색어, keyword
            if (keyword != null) {
                try {
                    stringBuilder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            link = stringBuilder.toString();

        } // if null 닫는 태그
        return link;
    } // getLink 닫는 태그

}
