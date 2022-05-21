package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long userId;

    @Size(min = 1, message = "orderInfo에 포함되는 orderItem이 1개 이상이어야 합니다.",
            groups = { ValidationGroups.normal.class })
    @Builder.Default
    private List<OrderItemDto> orderItems = new ArrayList<>();

}
