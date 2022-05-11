package com.leehyeonmin.book_project.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Data
@SuperBuilder
@NoArgsConstructor
public class BaseDto {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected Long id;
}
