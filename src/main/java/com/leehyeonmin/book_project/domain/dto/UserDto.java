package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.*;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder

public class UserDto extends BaseDto {

    private String name;

    private String email;

    private String gender;

    private AddressDto homeAddress;

    private AddressDto companyAddress;

    public UserDto(User user){
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        gender = user.getGender().toString();
        homeAddress = new AddressDto(user.getHomeAddress());
        companyAddress = new AddressDto(user.getCompanyAddress());
    }

}
