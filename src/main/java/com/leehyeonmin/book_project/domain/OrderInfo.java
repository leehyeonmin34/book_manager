package com.leehyeonmin.book_project.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
public class OrderInfo extends BaseEntity{
    @Id
    @Column(name = "ORDER_INFO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "USER_ID")
    @ToString.Exclude
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItem... orderItems){
        Collections.addAll(this.orderItems, orderItems);
        for(OrderItem orderItem : orderItems){
            orderItem.setOrderInfo(this);
        }
    }

}
