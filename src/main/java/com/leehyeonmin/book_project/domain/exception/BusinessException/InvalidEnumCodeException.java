package com.leehyeonmin.book_project.domain.exception.BusinessException;

import com.leehyeonmin.book_project.domain.exception.ErrorCode;

public class InvalidEnumCodeException extends BusinessException{

    public InvalidEnumCodeException(String msg){
        super(ErrorCode.INVALID_INPUT_VALUE, msg);
    }
    public InvalidEnumCodeException(ErrorCode errorCode, String msg){
        super(errorCode, msg);
    }
}
