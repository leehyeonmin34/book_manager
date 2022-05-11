package com.leehyeonmin.book_project.domain.exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException{

    private ErrorCode errorCode;

    public MyException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MyException(ErrorCode errorCode, String msg){
        super(msg);
        this.errorCode = errorCode;
    }

}
