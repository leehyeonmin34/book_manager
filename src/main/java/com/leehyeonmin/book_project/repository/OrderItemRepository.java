package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
