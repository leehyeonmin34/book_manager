package com.leehyeonmin.book_project.domain;

import com.leehyeonmin.book_project.domain.Enum.BookStatus;
import com.leehyeonmin.book_project.domain.Enum.Category;
import com.leehyeonmin.book_project.domain.converter.BookStatusConverter;
import com.leehyeonmin.book_project.domain.converter.CategoryConverter;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Convert(converter = CategoryConverter.class)
    private Category category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    @Builder.Default
    @Convert(converter = BookStatusConverter.class)
    private BookStatus status = BookStatus.AVAILABLE;

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

    public void updateBasicInfo(String name, Category category){
        this.name = name;
        this.category = category;
    }

    public void updateStatus(BookStatus status){
        this.status = status;
    }

    public void addBookAndAuthor(BookAndAuthor bookAndAuthor){
        this.getBookAndAuthors().add(bookAndAuthor);
        if( bookAndAuthor.getBook() != this){
            bookAndAuthor.updateBook(this);
        }
    }


}
