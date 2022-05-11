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

    private String category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    @Builder.Default
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthor(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }

    @Builder.Default
    private BookStatus status = new BookStatus(BookStatus.AVALABLE);

    @JoinColumn(name = "PUBLISHER_ID")
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Publisher publisher;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "BOOK_REVIEW_INFO_ID")
    private BookReviewInfo bookReviewInfo;

    @Builder.Default
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Review> reviews = new ArrayList<>();

    public void updatePublisher(Publisher publisher){
        this.publisher = publisher;
    }

    public void updateBasicInfo(String name, String category){
        this.name = name;
        this.category = category;
    }

    public void updateStatus(int code){
        this.status.updateBookStatus(code);
    }


}
