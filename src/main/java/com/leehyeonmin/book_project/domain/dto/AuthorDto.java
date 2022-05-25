package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
public class AuthorDto extends BaseDto{

    @NotBlank(message = "authorDto.name은 빈 값일 수 없습니다.",
            groups = { ValidationGroups.normal.class })
    private String name;

    @NotBlank(message = "authorDto.country는 빈 값일 수 없습니다.",
            groups = { ValidationGroups.normal.class })
    private String country;

    public AuthorDto(Author author){
        id = author.getId();
        name = author.getName();
        country = author.getCountry();
    }

}
