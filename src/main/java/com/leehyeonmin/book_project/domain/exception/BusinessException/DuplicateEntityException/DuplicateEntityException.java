package com.leehyeonmin.book_project.domain.exception.BusinessException.DuplicateEntityException;

import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.ErrorCode;

public class DuplicateEntityException extends BusinessException {
    public DuplicateEntityException(String msg){
        super(ErrorCode.INVALID_INPUT_VALUE, msg);
    }
    public DuplicateEntityException(ErrorCode errorCode, String msg){
        super(errorCode, msg);
    }
}
