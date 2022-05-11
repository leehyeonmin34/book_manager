package com.leehyeonmin.book_project.domain.dto;


import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder

public class ReviewDto extends BaseDto {

    private String name;

    private String content;

    private float score;

    private Long bookId;

    private Long userId;
}
