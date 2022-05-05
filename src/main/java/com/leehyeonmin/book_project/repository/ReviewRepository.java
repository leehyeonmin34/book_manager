package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByUserId(Long id);
}
