package com.leehyeonmin.book_project.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderItemUpdateRequest {
    Long bookId;
    int price;
    int ea;
}
