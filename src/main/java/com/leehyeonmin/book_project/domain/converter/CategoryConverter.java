package com.leehyeonmin.book_project.domain.converter;

import com.leehyeonmin.book_project.domain.Enum.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category attribute) {
        return attribute != null ? attribute.getCode() : "-1";
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        return dbData != null ? Category.ofCode(dbData) : null;
    }
}
