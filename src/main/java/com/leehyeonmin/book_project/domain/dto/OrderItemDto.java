package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto{

    private Long id;

    private int ea;

    private LocalDateTime arriveDate;

    private Long bookId;

    private Long orderInfoId;

    public OrderItemDto(OrderItem orderItem){
        id = orderItem.getId();
        ea = orderItem.getEa();
        arriveDate = orderItem.getArriveDate();
        bookId = orderItem.getBook().getId();
        orderInfoId = orderItem.getOrderInfo().getId();
    }

}
