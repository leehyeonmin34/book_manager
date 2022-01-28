package com.leehyeonmin.book_project.domain.converter;

import com.leehyeonmin.book_project.domain.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : new BookStatus(dbData);
    }
}
