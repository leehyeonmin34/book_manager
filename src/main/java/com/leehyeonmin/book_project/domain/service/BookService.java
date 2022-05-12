package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.dto.ReviewDto;
import com.leehyeonmin.book_project.domain.request.AddBookRequest;
import com.leehyeonmin.book_project.domain.response.BooksResponse;

import java.util.List;

public interface BookService {
    public BooksResponse getAllBooks();

    public BookDto getBook(Long id);

    BookDto addBook(BookDto dto);

    public BookDto modifyBasicInfo(Long id, String name, String category);

    public void changeBookStatus(Long id, int statusCode);

    public BookDto changeAuthorOfBook(Long bookId, Long authorId);

    public BookDto changePublisherOfBook(Long bookId, Long publisherId);

    public void removeBook(Long bookId);

    public List<ReviewDto> getAllReviews(Long bookId);
}
