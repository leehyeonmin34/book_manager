package com.leehyeonmin.book_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class BookStatus {
    private int code;
    private String description;

    public BookStatus(){
        this.code = 100;
        this.description = parseDescription(100);
    }

    public BookStatus(int code){
        this.code = code;
        this.description = parseDescription(code);
    }

    public void setBookStatus(int code){
        this.code = code;
        this.description = parseDescription(code);
    }

    public String parseDescription(int code){
        switch (code) {
            case 100:
                return "대여 가능";
            case 200:
                return "대여중";
            case 300:
                return "대여 불가";
            default:
                return "잘못된 상태";
        }
    }
}

