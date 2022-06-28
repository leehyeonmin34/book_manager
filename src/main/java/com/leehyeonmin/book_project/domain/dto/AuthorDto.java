package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        createdAt = author.getCreatedAt();
        updatedAt = author.getUpdatedAt();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetResponse extends BaseDto{
        private String name;
        private String country;

        public GetResponse(Author author){
            id = author.getId();
            createdAt = author.getCreatedAt();
            updatedAt = author.getUpdatedAt();
            name = author.getName();
            country = author.getCountry();
        }
    }


    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetListResponse extends BaseDto{

        @Builder.Default
        private List<AuthorDto.GetResponse> authors = new ArrayList<>();

        @Builder.Default
        private int total = 0;

        public GetListResponse(List<Author> entities) {
            this.authors = entities.stream().map(item -> new AuthorDto.GetResponse(item)).collect(Collectors.toList());
            this.total = authors.size();
        }
    }


    @Getter
    @Builder
    @AllArgsConstructor
    static public class Request extends BaseDto{
        private String name;
        private String country;

        public AuthorDto toServiceDto() {
            return AuthorDto.builder()
                    .id(id)
                    .name(name)
                    .country(country)
                    .build();
        }
    }



}
