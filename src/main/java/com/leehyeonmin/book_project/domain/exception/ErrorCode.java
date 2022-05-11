package com.leehyeonmin.book_project.domain.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C003", "Entity Not Found"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "C006", "Access is Denied"), // Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "C005", " Invalid Type Value"),

    // Member
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST.value(), "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(HttpStatus.BAD_REQUEST.value(), "M002", "Login input is invalid"),

    // Book

    // Author

    ;

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public int getStatus() { return this.status; }
    public String getCode() { return this.code; }
}