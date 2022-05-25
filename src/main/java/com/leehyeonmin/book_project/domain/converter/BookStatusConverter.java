package com.leehyeonmin.book_project.domain.converter;

import com.leehyeonmin.book_project.domain.Enum.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BookStatusConverter implements AttributeConverter<BookStatus, String> {

    @Override
    public String convertToDatabaseColumn(BookStatus attribute) {

        return attribute != null ? attribute.getCode() : "-1";
    }

    @Override
    public BookStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : BookStatus.ofCode(dbData);
    }
}
