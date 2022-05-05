package com.leehyeonmin.book_project.domain.request;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderInfoUpdateRequest {
    List<OrderItemUpdateRequest> orderItems = new ArrayList<>();
    int totalPrice;
    int totalNum;
    Long userId;
}
