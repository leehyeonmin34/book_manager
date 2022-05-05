package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.BookAndAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAndAuthorRepository extends JpaRepository<BookAndAuthor, Long> {
    public List<BookAndAuthor> getByBookId(Long id);

    public List<BookAndAuthor> getByAuthorId(Long id);
}
