package com.leehyeonmin.book_project.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity{

    @Id
    @Column(name = "BOOK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthor(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }

    private String category;

    @Builder.Default
    private BookStatus status = new BookStatus();

    public void setStatus(int code){
        this.status.setBookStatus(code);
    }

    @JoinColumn(name = "PUBLISHER_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private Publisher publisher;

    @OneToOne
    @JoinColumn(name = "BOOK_REVIEW_INFO_ID")
    private BookReviewInfo bookReviewInfo;

    @Builder.Default
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();



}
