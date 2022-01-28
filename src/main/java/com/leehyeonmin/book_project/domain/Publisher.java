package com.leehyeonmin.book_project.domain;


import com.leehyeonmin.book_project.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Publisher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @JoinColumn(name = "publisher_id")
    @OneToMany(fetch = FetchType.EAGER)
    List<Book> books = new ArrayList<>();

    public void addBooks(Book... books) {
        Collections.addAll(this.books, books);
    }
}