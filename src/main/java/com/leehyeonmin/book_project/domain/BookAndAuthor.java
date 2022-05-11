package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
public class BookAndAuthor extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public void updateBook(Book book){
        this.book = book;
    }

    public void updateAuthor(Author author){
        this.author = author;
    }

}
