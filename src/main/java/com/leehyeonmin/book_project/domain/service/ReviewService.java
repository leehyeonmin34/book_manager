package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    public List<ReviewDto> findAllReviews();

    public ReviewDto findReview(Long id);

    public ReviewDto addReview(ReviewDto dto);

    public ReviewDto modifyReview(ReviewDto dto);

    public Boolean removeReview(Long id);
}
