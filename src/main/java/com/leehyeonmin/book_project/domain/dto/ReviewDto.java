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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder

public class ReviewDto extends BaseDto {

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String name;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String content;

    @NotBlank
    @DecimalMax(value = "5.0", groups = { ValidationGroups.normal.class })
    @DecimalMin(value = "0.0", groups = { ValidationGroups.normal.class })
    private float score;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long bookId;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private Long userId;
}
