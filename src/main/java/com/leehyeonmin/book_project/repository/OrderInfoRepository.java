package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    public List<OrderInfo> findByUserId(Long id);
}
