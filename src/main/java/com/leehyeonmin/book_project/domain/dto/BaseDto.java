package com.leehyeonmin.book_project.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Data
public class BaseDto {
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
