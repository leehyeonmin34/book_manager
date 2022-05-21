package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Address;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddressDto {
    @NotEmpty(message = "city는 빈 값이 아니어야 합니다.",
            groups = { ValidationGroups.normal.class })
    private String city;

    @NotEmpty(message = "district는 빈 값이 아니어야 합니다.",
            groups = { ValidationGroups.normal.class })
    private String district;

    @NotEmpty(message = "addressDetail은 빈 값이 아니어야 합니다.",
            groups = { ValidationGroups.normal.class })
    private String addressDetail;

    private String zipCode;

}
