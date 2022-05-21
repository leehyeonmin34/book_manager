package com.leehyeonmin.book_project.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class BookReviewInfoDto extends BaseDto{

    private float averageReviewScore;

    private int reviewCount;

}
