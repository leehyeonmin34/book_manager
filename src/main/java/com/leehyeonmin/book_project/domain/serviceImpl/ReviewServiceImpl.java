package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Review;
import com.leehyeonmin.book_project.domain.dto.ReviewDto;
import com.leehyeonmin.book_project.domain.service.ReviewService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    final private ReviewRepository reviewRepository;

    final private ToEntity toEntity;

    final private ToDto toDto;

    @Override
    public List<ReviewDto> findAllReviews() {
        return reviewRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto findReview(Long id) {
        Review entity = reviewRepository.findById(id).orElse(null);
        return toDto.from(entity);
    }

    @Override
    public ReviewDto addReview(ReviewDto dto) {
        Review entity = toEntity.from(dto);
        Review saved = reviewRepository.save(entity);
        return toDto.from(saved);
    }

    @Override
    public ReviewDto modifyReview(ReviewDto dto) {
        if(reviewRepository.findById(dto.getId()).isPresent()){
            return addReview(dto);
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeReview(Long id) {
        if(reviewRepository.findById(id).isPresent()){
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
