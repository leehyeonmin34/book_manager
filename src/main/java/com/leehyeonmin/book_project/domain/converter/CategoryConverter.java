package com.leehyeonmin.book_project.domain.converter;

import com.leehyeonmin.book_project.domain.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Category attribute) {
        return attribute.getCode();
    }

    @Override
    public Category convertToEntityAttribute(Integer dbData) {
        return dbData != null ? new Category(dbData) : null;
    }
}
