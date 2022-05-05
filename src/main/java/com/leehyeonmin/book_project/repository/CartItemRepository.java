package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public List<CartItem> findByUserId(Long id);
}
