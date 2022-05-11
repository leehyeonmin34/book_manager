package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.BookReviewInfo;
import com.leehyeonmin.book_project.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewInfoRepository extends JpaRepository<BookReviewInfo, Long> {

}
