package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.Enum.BookStatus;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor

public class BookDto extends BaseDto{


    private String name;

    private String categoryCode;

    private String categoryName;

    private Long publisherId;

    private String publisherName;

    @Builder.Default
    private String statusCode = BookStatus.AVAILABLE.getCode();

    @Builder.Default
    private String statusDesc = BookStatus.AVAILABLE.getDescription();

    private List<AuthorDto> authors;

    private float averageScore;

    private Long bookReviewInfoId;

    private int reviewCount;

    public BookDto(Book book){
        id = book.getId();
        name = book.getName();
        if(book.getCategory() != null) {
            categoryName = book.getCategory().getDesc();
            categoryCode = book.getCategory().getCode();
        }
        if(book.getStatus() != null) {
            statusCode = book.getStatus().getCode();
            statusDesc = book.getStatus().getDescription();
        }
        if(book.getPublisher() != null) {
            publisherName = book.getPublisher().getName();
            publisherId = book.getPublisher().getId();
        }
        if(book.getBookReviewInfo() != null) {
            averageScore = book.getBookReviewInfo().getAverageReviewScore();
            reviewCount = book.getBookReviewInfo().getReviewCount();
            bookReviewInfoId = book.getBookReviewInfo().getId();
        }
        authors = book.getBookAndAuthors().stream()
                .map(item -> new AuthorDto(item.getAuthor())).collect(Collectors.toList());
    }


    @Getter
    @Setter
    @NoArgsConstructor
    static public class AddRequest{

        @NotBlank(message = "??? ????????? ??? ?????? ??? ????????????.",
                groups = {ValidationGroups.normal.class})
        private String name;

        private String categoryCode;

        private String statusCode = BookStatus.AVAILABLE.getCode();

        @NotBlank(message = "????????? ???????????? ??? ?????? ??? ????????????.",
                groups = {ValidationGroups.normal.class})
        private Long publisherId;

        @Size(min = 1, message = "????????? 1??? ????????????????????????.",
                groups = {ValidationGroups.normal.class})
        private List<Long> authorIdList;

        public BookDto toServiceDto(){
            return BookDto.builder()
                    .name(name)
                    .categoryCode(categoryCode)
                    .statusCode(statusCode)
                    .publisherId(publisherId)
                    .authors(authorIdList.stream().map(
                            id -> AuthorDto.builder().id(id).build()
                    ).collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetResponse{

        private Long id;

        private String name;

        private String categoryName;

        private String categoryCode;

        private String publisherName;

        private Long publisherId;

        private float averageScore;

        private int reviewCount;

        private Long bookReviewInfoId;

        @Builder.Default
        private String statusCode = BookStatus.AVAILABLE.getCode();

        @Builder.Default
        private String statusDesc = BookStatus.AVAILABLE.getDescription();

        @Builder.Default
        private AuthorDto.GetListResponse authors = AuthorDto.GetListResponse.builder().build();

        public GetResponse(Book book){

            id = book.getId();
            name = book.getName();
            if(book.getCategory() != null) {
                categoryName = book.getCategory().getDesc();
                categoryCode = book.getCategory().getCode();
            }
            if(book.getStatus() != null) {
                statusCode = book.getStatus().getCode();
                statusDesc = book.getStatus().getDescription();
            }
            if(book.getPublisher() != null) {
                publisherName = book.getPublisher().getName();
                publisherId = book.getPublisher().getId();
            }
            if(book.getBookReviewInfo() != null) {
                averageScore = book.getBookReviewInfo().getAverageReviewScore();
                reviewCount = book.getBookReviewInfo().getReviewCount();
                bookReviewInfoId = book.getBookReviewInfo().getId();
            }

            authors = new AuthorDto.GetListResponse(
                    book.getBookAndAuthors().stream().map(item -> item.getAuthor()).collect(Collectors.toList())
            );

        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    static public class GetListResponse{
        @Builder.Default
        private List<GetResponse> books = new ArrayList<>();

        @Builder.Default
        private int total = 0;

        public GetListResponse(List<Book> books){
            this.books = books.stream().map( book -> new GetResponse(book))
            .collect(Collectors.toList());
            this.total = books.size();
        }
    }


}
