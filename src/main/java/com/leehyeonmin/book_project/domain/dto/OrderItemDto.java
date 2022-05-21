package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class OrderItemDto  extends BaseDto{

    @NotBlank(groups = { ValidationGroups.normal.class })
    private int ea;

    private LocalDateTime arriveDate;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long bookId;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long orderInfoId;

}
