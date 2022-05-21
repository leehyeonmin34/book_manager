package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class CartItemDto extends BaseDto{

    @Max(99)
    private int ea;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long userId;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long bookId;
}
