package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.BookReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewInfoRepository extends JpaRepository<BookReviewInfo, Long> {
}
