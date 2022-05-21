package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String name;

    @NotBlank
    @Email(groups = { ValidationGroups.normal.class })
    private String email;

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String gender;

    @Valid
    private AddressDto homeAddress;

    @Valid
    private AddressDto companyAddress;

}
