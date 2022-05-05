package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    public List<UserHistory> findByUserId(Long id);
}
