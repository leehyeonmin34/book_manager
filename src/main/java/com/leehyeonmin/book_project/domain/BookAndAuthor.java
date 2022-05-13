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

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public void updateBook(Book book){
        if(this.book != null){
            this.book.getBookAndAuthors().remove(this);
        }
        this.book = book;
        if(!book.getBookAndAuthors().contains(this)) {
            book.addBookAndAuthor(this);
        }
    }

    public void updateAuthor(Author author){
        if(this.author != null){
            this.author.getBookAndAuthors().remove(this);
        }
        this.author = author;
        if(!author.getBookAndAuthors().contains(this)) {
            author.addBookAndAuthor(this);
        }
    }




}
