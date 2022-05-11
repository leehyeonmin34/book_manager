package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class RepoUtilsTest {


    @InjectMocks
    private RepoUtils repoUtils;

    @Mock
    private BookRepository bookRepository;


    @Test
    void getOneElseThrowExceptionTest(){

        //given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(Book.builder().id(1L).build()));

        // when
        Book result = repoUtils.getOneElseThrowException(bookRepository, 1L);

        // then
        assertThat(result).isNotNull();
        System.out.println(result);
    }

}
