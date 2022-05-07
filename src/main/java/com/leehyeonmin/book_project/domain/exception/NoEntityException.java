package com.leehyeonmin.book_project.domain.exception;

public class NoEntityException extends RuntimeException{
    public NoEntityException(String msg){
        super(msg);
    }
}
