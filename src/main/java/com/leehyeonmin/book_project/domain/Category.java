package com.leehyeonmin.book_project.domain;

import lombok.*;
import org.modelmapper.config.Configuration;

import javax.persistence.Access;
import javax.persistence.Column;

@Builder
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity{

    @Column(unique = true)
    private int code;

    private String name;

    static final public int GENERAL = 000;
    static final public int PHILOSOPHY = 100;
    static final public int RELIGION = 200;
    static final public int SOCIETY = 300;
    static final public int NATURE = 400;
    static final public int TECH = 500;
    static final public int ART = 600;
    static final public int LANGUAGE = 700;
    static final public int LITERATURE = 800;
    static final public int HISTORY = 900;

    private String parseDescription(int code){
        switch (code) {
            case GENERAL: return "총류";
            case PHILOSOPHY: return "철학";
            case RELIGION: return "종교";
            case SOCIETY: return "사회";
            case NATURE: return "자연과학";
            case TECH: return "기술괴학";
            case LANGUAGE: return "언어";
            case LITERATURE: return "문학";
            case HISTORY: return "역사";
            default: return "잘못된 카테고리 코드";
        }
    }

    public Category(int code){
        this.code = code;
        this.name = parseDescription(code);
    }
}
