package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)

public class OrderInfoDto extends BaseDto{

    private LocalDateTime orderDate;

    private Long userId;

    @Builder.Default
    private List<OrderItemDto> orderItems = new ArrayList<>();

}
