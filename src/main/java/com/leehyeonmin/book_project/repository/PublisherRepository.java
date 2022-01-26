package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
