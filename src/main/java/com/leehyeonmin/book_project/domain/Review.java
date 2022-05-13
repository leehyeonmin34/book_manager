package com.leehyeonmin.book_project.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class Review extends BaseEntity{

    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private float score;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "USER_ID")
    private User user;

}
