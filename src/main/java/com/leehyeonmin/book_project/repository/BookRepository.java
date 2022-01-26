package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
