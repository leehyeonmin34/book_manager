package com.leehyeonmin.book_project.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address{
    private String city; // 시

    private String district; // 구

    private String detail; // 상세주소

    private String zipCode; // 우편번호
}
