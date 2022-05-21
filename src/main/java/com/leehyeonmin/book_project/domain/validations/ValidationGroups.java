package com.leehyeonmin.book_project.domain.validations;

public class ValidationGroups {
    public interface normalAndIdNotNull {};
    public interface normal extends normalAndIdNotNull {};
    public interface idNotNull extends normalAndIdNotNull {};
}
