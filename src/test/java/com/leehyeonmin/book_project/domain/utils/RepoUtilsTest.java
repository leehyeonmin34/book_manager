package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class RepoUtilsTest {


    @InjectMocks
    private RepoUtils repoUtils;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("getOneElseThrowException 실패")
    public void getOneElseThrowExceptionTestFail(){

        //given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // when - then
        assertThatThrownBy(()-> repoUtils.getOneElseThrowException(bookRepository, 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    @DisplayName("getOneElseThrowException 성공")
    public void getOneElseThrowExceptionTestSuccess(){

        //given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(Book.builder().id(1L).build()));

        // when
        Book result = repoUtils.getOneElseThrowException(bookRepository, 1L);

        // then
        assertThat(result).isNotNull();
        System.out.println(result);

    }

    @Test
    @DisplayName("deleteOneElseThrowException 실패")
    public void deleteOneElseThrowExceptionTestFail(){
        //given
        when(bookRepository.existsById(any(Long.class))).thenReturn(false);

        // when - then
        assertThatThrownBy(()-> repoUtils.deleteOneElseThrowException(bookRepository, 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("deleteOneElseThrowException 성공")
    public void deleteOneElseThrowExceptionTestSuccess(){

        //given
        when(bookRepository.existsById(any(Long.class))).thenReturn(true);

        // when - then
        assertThatCode(() -> repoUtils.deleteOneElseThrowException(bookRepository, 1L))
                .doesNotThrowAnyException();
    }



    private List<Book> givenBookList(){
        List<Book> lst = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Book book = Book.builder()
                    .name("책 이름")
                    .id(999L)
                    .build();
            lst.add(book);
        }
        return lst;
    }

}
