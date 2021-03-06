package com.leehyeonmin.book_project.domain;


import com.leehyeonmin.book_project.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Publisher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PUBLISHER_ID")
    private Long id;

    private String name;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "publisher")
    List<Book> books = new ArrayList<>();

    public void addBooks(Book... books) {
        Collections.addAll(this.books, books);
        for(Book book : books){
            this.books.add(book);
            if(book.getPublisher() != this)
                book.updatePublisher(this);
        }
    }

    public void updateBasicInfo(String name){
        this.name = name;
    }
}
