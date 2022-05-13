package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Author extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthor(BookAndAuthor bookAndAuthor){
        bookAndAuthors.add(bookAndAuthor);
        if( bookAndAuthor.getAuthor() != this){
            bookAndAuthor.updateAuthor(this);
        }
    }

    public void updateBasicInfo(String name, String country){
        this.name = name;
        this.country = country;
    }

}
