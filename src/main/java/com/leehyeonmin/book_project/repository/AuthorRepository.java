package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
