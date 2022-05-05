package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.OrderItemDto;

import java.util.List;

public interface OrderItemService {

    public List<OrderItemDto> findAllOrderItem();

    public void removeOrderItem(Long id);

    public void modifyOrderItem(OrderItemDto orderItemDto);
}
