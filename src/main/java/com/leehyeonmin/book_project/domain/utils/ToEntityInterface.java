package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.dto.BookDto;

public interface ToEntityInterface {
    EntityInterface from(DtoInterface dto);

}
