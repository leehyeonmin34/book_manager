package com.leehyeonmin.book_project.domain.Enum;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CategoryTest {

    @Test
    public void ofCodeTestSuccess(){
        // given
        String code = "500";
        String expectingDesc = "기술과학";

        // when
        Category result = Category.ofCode(code);

        // then
        assertThat(result).isEqualTo(Category.TECH);
    }

}
