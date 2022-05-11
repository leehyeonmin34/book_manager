package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.repository.AuthorRepository;
import jdk.jfr.Name;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void AuthorRepositoryTestIsNotNull(){
        assertThat(authorRepository).isNotNull();
    }

}
