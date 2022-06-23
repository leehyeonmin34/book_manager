package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.Enum.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from book order by id desc limit 1", nativeQuery = true)
    Map<String, Object> findRawRecord();

    public Long countByCategory(String category);

    public Page<Book> findByCategory(Category category, Pageable pageable);

    public Page<Book> findAll(Pageable pageable);
}
