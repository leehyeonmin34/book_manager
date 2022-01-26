package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class BookAndAuthor extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Book book;

}
