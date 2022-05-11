package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BookAndAuthor;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AuthorDto extends BaseDto{

    private String name;

    private String country;

}
