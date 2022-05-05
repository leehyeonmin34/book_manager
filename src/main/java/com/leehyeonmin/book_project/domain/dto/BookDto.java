package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;

    private String name;

    private String category;

    @Builder.Default
    private BookStatus status = new BookStatus();

    private Long publisherId;

    private Long bookReviewInfoId;

    public BookDto(Book book){
        id = book.getId();
        name = book.getName();
        category = book.getCategory();
        status = book.getStatus();
        publisherId = book.getPublisher().getId();
        bookReviewInfoId = book.getBookReviewInfo().getId();
    }

}
