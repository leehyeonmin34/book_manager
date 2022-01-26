package com.leehyeonmin.book_project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
//    @ToString.Exclude
//    @OneToOne(optional = false, mappedBy = "bookReviewInfo")
    private Book book;

    private float averageReviewScore;

    private int reviewCount;
}
