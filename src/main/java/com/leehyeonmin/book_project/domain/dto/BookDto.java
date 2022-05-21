package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BookStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class BookDto extends BaseDto{

    private String name;

    private String category;

    @Builder.Default
    private int status = BookStatus.AVALABLE;

    private Long publisherId;

    private Long authorId;

    private Long bookReviewInfoId;


}
