package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> getByUserId(Long id);

    public List<Review> getByBookId(Long id);
}
