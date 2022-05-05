package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.OrderInfoDto;
import com.leehyeonmin.book_project.domain.request.OrderInfoUpdateRequest;

import java.util.List;

public interface OrderInfoService {
    public OrderInfoDto addOrderInfo(OrderInfoUpdateRequest orderInfoUpdateRequest);

    public List<OrderInfoDto> findAllOrderInfo();

    public OrderInfoDto findOrderInfo(Long id);

    public Boolean removeOrderInfo(Long id);

    public OrderInfoDto modifyOrderInfo(OrderInfoDto orderInfoDto);
}
