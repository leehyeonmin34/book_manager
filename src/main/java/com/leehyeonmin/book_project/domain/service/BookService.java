package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    public List<BookDto> findAllBooks();

    public BookDto findBook(Long id);

    public BookDto addBook(BookDto dto);

    public BookDto modifyBook(BookDto dto);

    public Boolean removeBook(Long id);
}
