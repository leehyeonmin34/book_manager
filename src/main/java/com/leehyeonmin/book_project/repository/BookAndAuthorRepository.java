package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.BookAndAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookAndAuthorRepository extends JpaRepository<BookAndAuthor, Long> {
    public List<BookAndAuthor> findByBookId(Long id);

    public List<BookAndAuthor> findByAuthorId(Long id);

    public Optional<BookAndAuthor> findByAuthorIdAndBookId(Long authorId, Long bookId);
}
