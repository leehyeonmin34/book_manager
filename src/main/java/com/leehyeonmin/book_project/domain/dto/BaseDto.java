package com.leehyeonmin.book_project.domain.dto;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.utils.DtoInterface;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDto implements DtoInterface {
    @NotBlank(groups = { ValidationGroups.idNotNull.class })
    protected Long id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

}
