package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
