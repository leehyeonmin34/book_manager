package com.leehyeonmin.book_project.domain.Enum;

import com.leehyeonmin.book_project.domain.converter.CategoryConverter;
import com.leehyeonmin.book_project.domain.exception.BusinessException.InvalidEnumCodeException;
import lombok.*;

import javax.persistence.Convert;
import java.util.Arrays;

@AllArgsConstructor
@Getter
@Convert(converter = CategoryConverter.class)
public enum Category{
    GENERAL("총류", "000"),
    PHILOSOPHY("철학", "100"),
    RELIGION("종교", "200"),
    SOCIETY("사회", "300"),
    NATURE("자연과학", "400"),
    TECH("기술과학", "500"),
    ART("예술", "600"),
    LANGUAGE("언어","700"),
    LITERATURE("문학", "800"),
    HISTORY("역사","900"),
    NONE("카테고리 없음", "-1"),
    ALL("모든 카테고리", "0");

    private final String desc;
    private final String code;

    public static Category ofCode(String code) {
        return Arrays.stream(Category.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidEnumCodeException("잘못된 category enum code입니다. 입력된 code: " + code));
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
