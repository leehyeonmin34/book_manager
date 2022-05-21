package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
public class PublisherDto extends BaseDto{

    @NotBlank(groups = { ValidationGroups.normal.class })
    private String name;

}
