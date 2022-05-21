package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Category;
import lombok.Getter;

@Getter
public class CategoryDto {

    private int code;
    private String name;

    public CategoryDto(int code){
        Category category = new Category(code);
        this.code = category.getCode();
        this.name = category.getName();
    }

}
