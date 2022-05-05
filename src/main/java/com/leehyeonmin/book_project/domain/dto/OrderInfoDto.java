package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {

    private Long id;

    private LocalDateTime orderDate;

    private Long userId;

    private List<OrderItemDto> orderItems = new ArrayList<>();

    public OrderInfoDto(OrderInfo orderInfo){
        id = orderInfo.getId();
        orderDate = orderInfo.getOrderDate();
        userId = orderInfo.getUser().getId();
        orderItems = orderInfo.getOrderItems().stream().map(item -> new OrderItemDto(item)).collect(Collectors.toList());
    }

}
