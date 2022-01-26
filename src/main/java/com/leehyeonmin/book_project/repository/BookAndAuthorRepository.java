package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.BookAndAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAndAuthorRepository extends JpaRepository<BookAndAuthor, Long> {
}
