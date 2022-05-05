package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    private int ea;

    private LocalDateTime arriveDate;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "ORDER_INFO_ID")
    private OrderInfo orderInfo;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Builder
    public OrderItem(int ea, LocalDateTime arriveDate, OrderInfo orderInfo, Book book) {
        this.ea = ea;
        this.arriveDate = arriveDate;
        this.orderInfo = orderInfo;
        this.book = book;
    }
}

