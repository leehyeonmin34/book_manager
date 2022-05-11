package com.leehyeonmin.book_project.domain.request;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ModifyAuthorOfBookRequest {

    private Long bookId;

    private AuthorDto authorDto;
}
