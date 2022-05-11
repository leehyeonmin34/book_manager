package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.dto.BaseDto;

public interface ToEntityInterface {
    public <E extends BaseEntity, D extends BaseDto> E from(D dto);
}
