package com.leehyeonmin.book_project.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity{

    @Id
    @Column(name = "BOOK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Category category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    @Builder.Default
    private BookStatus status = new BookStatus(BookStatus.AVALABLE);

    @JoinColumn(name = "PUBLISHER_ID")
    @ManyToOne
    private Publisher publisher;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "BOOK_REVIEW_INFO_ID")
    private BookReviewInfo bookReviewInfo;

    @Builder.Default
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public void updatePublisher(Publisher publisher){
        if(publisher == null){
            this.publisher = null;
            return;
        }
        if(this.publisher != null){
            this.publisher.getBooks().remove(this);
        }
        this.publisher = publisher;
        publisher.addBooks(this);
    }

    public void updateBasicInfo(String name, int categoryCode){
        this.name = name;
        this.category = new Category(categoryCode);
    }

    public void updateStatus(int code){
        this.status.updateBookStatus(code);
    }

    public void addBookAndAuthor(BookAndAuthor bookAndAuthor){
        this.getBookAndAuthors().add(bookAndAuthor);
        if( bookAndAuthor.getBook() != this){
            bookAndAuthor.updateBook(this);
        }
    }


}
