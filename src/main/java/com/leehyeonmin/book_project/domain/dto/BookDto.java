package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BookStatus;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import jdk.jfr.Category;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class BookDto extends BaseDto{

    @Data
    static public class Info {
        private String name;

        private String category;

        private Long publisherId;

        private List<Long> authorIdList;

        private Long bookReviewInfoId;

        private int status = BookStatus.AVALABLE;
    }

    @Data
    static public class addRequest{
        @NotBlank(message = "책 이름은 빈 값일 수 없습니다.",
                groups = {ValidationGroups.normal.class})
        private String name;

        private String category;

        @Builder.Default
        private int status = BookStatus.AVALABLE;

        @NotBlank(message = "출판사 아이디는 빈 값일 수 없습니다.",
                groups = {ValidationGroups.normal.class})
        private Long publisherId;

        @Size(min = 1, message = "작가는 1명 이상이어야합니다.",
                groups = {ValidationGroups.normal.class})
        private List<Long> authorIdList;

        private Long bookReviewInfoId;
    }

    static public class getResponse{

        private String name;

        private int categoryCode;

        private String category;

        private PublisherDto publisher;

        private List<AuthorDto> authors;

        private BookReviewInfoDto bookReviewInfo;

    }

    static public class getListResponse{
        private List<getResponse> books;

        private int total;
    }


}
