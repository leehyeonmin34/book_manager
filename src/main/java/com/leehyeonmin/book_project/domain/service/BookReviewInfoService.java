package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.BookReviewInfoDto;

import java.util.List;

public interface BookReviewInfoService {
    public List<BookReviewInfoDto> findAllBookReviewInfos();

    public BookReviewInfoDto findBookReviewInfo(Long id);

    public BookReviewInfoDto addBookReviewInfo(BookReviewInfoDto dto);

    public BookReviewInfoDto modifyBookReviewInfo(BookReviewInfoDto dto);

    public Boolean removeBookReviewInfo(Long id);
}
