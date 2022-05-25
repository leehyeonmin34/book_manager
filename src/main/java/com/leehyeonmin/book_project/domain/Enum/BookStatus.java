package com.leehyeonmin.book_project.domain.Enum;

import com.leehyeonmin.book_project.domain.converter.BookStatusConverter;
import com.leehyeonmin.book_project.domain.exception.BusinessException.InvalidEnumCodeException;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@Convert( converter = BookStatusConverter.class )
public enum BookStatus {
    AVAILABLE("구매 가능", "100"),
    SOLD_OUT("품절", "200"),
    WAITING("입고 대기 중", "300"),
    NONE("상태 없음", "-1");

    private final String description;
    private final String code;

    public static BookStatus ofCode(String code){
        return Arrays.stream(BookStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException("BookStatus의 code값이 유요하지 않습니다. code : " + code));
    }

}

