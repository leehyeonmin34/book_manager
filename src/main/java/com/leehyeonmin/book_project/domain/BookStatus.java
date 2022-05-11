package com.leehyeonmin.book_project.domain;

import lombok.*;

@Getter
public class BookStatus {
    private int code;
    private String description;

    static final public int AVALABLE = 100;
    static final public int SOLDOUT = 200;
    static final public int WAITING = 300;

    public String parseDescription(int code){
        switch (code) {
            case AVALABLE:
                return "구매 가능";
            case SOLDOUT:
                return "품절";
            case WAITING:
                return "입고 대기 중";
            default:
                return "잘못된 상태";
        }
    }

    public BookStatus(int code){
        this.code = code;
        this.description = parseDescription(code);
    }

    public void updateBookStatus(int code){
        this.code = code;
        this.description = parseDescription(code);
    }
}

