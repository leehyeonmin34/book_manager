package com.leehyeonmin.book_project.domain.response;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class BooksResponse {
    @Builder.Default
    private List<BookDto> books = new ArrayList<>();

    @Builder.Default
    private int total = 0;
}
