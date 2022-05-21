package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@SuperBuilder
@NoArgsConstructor
public class BaseDto {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @NotBlank(groups = { ValidationGroups.idNotNull.class })
    protected Long id;
}
