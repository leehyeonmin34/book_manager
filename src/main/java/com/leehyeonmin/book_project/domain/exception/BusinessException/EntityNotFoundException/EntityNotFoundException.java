package com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException;

import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String msg){
        super(ErrorCode.ENTITY_NOT_FOUND, msg);
    }
    public EntityNotFoundException(ErrorCode errorCode, String msg){
        super(errorCode, msg);
    }
}
