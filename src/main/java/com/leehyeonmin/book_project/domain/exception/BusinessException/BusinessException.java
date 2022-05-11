package com.leehyeonmin.book_project.domain.exception.BusinessException;

import com.leehyeonmin.book_project.domain.exception.ErrorCode;
import com.leehyeonmin.book_project.domain.exception.MyException;

public class BusinessException extends MyException {


    public BusinessException(ErrorCode errorCode){
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String customeMessage){
        super(errorCode, customeMessage);
    }
}