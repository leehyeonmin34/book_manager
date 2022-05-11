package com.leehyeonmin.book_project.domain.exception.BusinessException.InvalidValueException;

import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(String msg){
        super(ErrorCode.INVALID_INPUT_VALUE, msg);
    }

    public InvalidValueException(ErrorCode errorCode, String msg){
        super(errorCode, msg);
    }
}
