package com.leehyeonmin.book_project.domain;


import com.leehyeonmin.book_project.domain.listener.UserEntityListener;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(UserEntityListener.class)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "home_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "district", column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "company_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zip_code"))
    })
    private Address companyAddress;


    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderInfo> orderInfos = new ArrayList<>();

    public void addOrderInfos(OrderInfo... orderInfos){
        Collections.addAll(this.orderInfos, orderInfos);
        for(OrderInfo orderInfo : orderInfos){
            orderInfo.setUser(this);
        }
    }

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

}


