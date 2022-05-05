package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ea;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

}
