package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BookAndAuthor;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {

    private Long id;

    private String name;

    private String country;

}
