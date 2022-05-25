package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public BookDto.GetListResponse getAllBooks();

    public BookDto.GetResponse getBook(Long id);

//    public BookDto.GetListResponse getBooksByCategory(String categoryCode, int start, int end);

//    public int getTotalByCategory(String categoryCode);

    Page<BookDto> getBooksByCategory(String categoryCode, Pageable pageable);

    public BookDto addBook(BookDto dto);

    public BookDto modifyBasicInfo(Long id, String name, String categoryCode);

    public void changeBookStatus(Long id, String statusCode);

    public BookDto changePublisherOfBook(Long bookId, Long publisherId);

    public void removeBook(Long bookId);

//    public List<ReviewDto.GetResponse> getAllReviews(Long bookId);
}
