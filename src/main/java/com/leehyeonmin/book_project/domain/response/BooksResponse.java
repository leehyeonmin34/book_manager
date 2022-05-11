package com.leehyeonmin.book_project.domain.response;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class BooksResponse {
    private List<BookDto> books = new ArrayList<>();
    private int total = 0;
}
