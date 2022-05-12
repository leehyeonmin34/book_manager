package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class OrderItemDto  extends BaseDto{

    private int ea;

    private LocalDateTime arriveDate;

    private Long bookId;

    private Long orderInfoId;

}
