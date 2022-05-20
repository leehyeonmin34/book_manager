package com.leehyeonmin.book_project.domain.service;


import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.DuplicateEntityException.DuplicateEntityException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.serviceImpl.AuthorServiceImpl;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ToEntity toEntity;

    @Mock
    private ToDto toDto;

    @Mock
    private RepoUtils repoUtils;


    @Test
    @DisplayName("author 추가 (성공)")
    public void addTestSuccess(){

        //given
        AuthorDto givenDtoWithoutId = givenDtoWithoutId();
        Author givenAuthorWithoutId = givenAuthorWithoutId();
        Author givenAuthorWithId = givenAuthorWithId();
        AuthorDto givenDtoWithId = givenDtoWithId();


        lenient().when(toEntity.from(any(AuthorDto.class))).thenReturn(givenAuthorWithoutId);
        lenient().when(authorRepository.save(any(Author.class))).thenReturn(givenAuthorWithId);
        lenient().when(toDto.from(any(Author.class))).thenReturn(givenDtoWithId);

        // when
        AuthorDto result =  authorService.addAuthor(givenDtoWithoutId);

        // then
        assertThat(result.getName()).isEqualTo(givenDtoWithoutId.getName());
        assertThat(result.getCountry()).isEqualTo(givenDtoWithoutId.getCountry());
    }


    @Test
    @DisplayName("author 제거 테스트 (성공)")
    public void removeTestSuccess(){
        // GIVEN
        Author givenAuthorWithId = Author.builder()
                .bookAndAuthors(givenBookAndAuthorListWithId())
                .name("이름")
                .build();

        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class))).thenReturn(givenAuthorWithId);

        // when
        authorService.removeAuthor(999L);

        // then
        assertThat(givenAuthorWithId.getBookAndAuthors().size()).isEqualTo(5);
        verify(bookAndAuthorRepository, times(1)).deleteAll(any(List.class));
        assertThatCode(() -> authorService.removeAuthor(999L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("author 제거 테스트 (0개 bookAndAuthor)")
    public void removeTestFail(){

        // GIVEN
        Author givenAuthorWithId = givenAuthorWithId();
        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class))).thenReturn(givenAuthorWithId);

        //when
        authorService.removeAuthor(999L);

        //then
        verify(bookAndAuthorRepository, times(0)).deleteAll(any(List.class));
        verify(authorRepository, times(1)).delete(any(Author.class));
        assertThatCode( () -> authorService.removeAuthor(999L)).doesNotThrowAnyException();
    }


    @Test
    @DisplayName("author 기본 정보 수정 테스트 (성공)")
    public void modifyTest(){
        // GIVEN
        Author givenAuthorWithId = givenAuthorWithId();
        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class))).thenReturn(givenAuthorWithId);
        lenient().when(authorRepository.save(any(Author.class))).thenReturn(givenAuthorWithId);
        lenient().when(toDto.from(any(Author.class))).thenReturn(givenDtoWithId());

        //when - then
        assertThatCode( () -> authorService.modifyBasicInfo(999L, "다른 이름", "다른 나라"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("author 수정 테스트 (실패)")
    public void modifyTestFail(){

        // given
        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        // when - then
        assertThatThrownBy(() -> authorService.modifyBasicInfo(999L, "다른 이름", "다른 나라"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("author에 book 추가 (성공)")
    public void addBookToAuthorSuccess(){
        //given
        lenient().when(bookAndAuthorRepository.findByAuthorIdAndBookId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());
        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class))).thenReturn(Author.builder().build());
        lenient().when(repoUtils.getOneElseThrowException(any(BookRepository.class), any(Long.class))).thenReturn(Book.builder().build());


        //when
        authorService.addBookToAuthor(999L, 999L);

        //then
        verify(bookAndAuthorRepository, times(1)).save(any(BookAndAuthor.class));
    }

    @Test
    @DisplayName("author에 book 추가 (실패)")
    public void addBookToAuthorFail(){
        //
        lenient().when(bookAndAuthorRepository.findByAuthorIdAndBookId(any(Long.class), any(Long.class))).thenReturn(Optional.of(givenBookAndAuthorWithId()));

        // when - then
        assertThatThrownBy(() -> authorService.addBookToAuthor(999L, 999L))
                .isInstanceOf(DuplicateEntityException.class);
    }

    @Test
    @DisplayName("author에 book 삭제 (실패)")
    public void removeBookFromAuthorFail(){
        lenient().when(bookAndAuthorRepository.findByAuthorIdAndBookId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        // when - then
        assertThatThrownBy(() -> authorService.removeBookFromAuthor(999L, 999L))
                .isInstanceOf(EntityNotFoundException.class);
    }



    public AuthorDto givenDtoWithoutId(){
        AuthorDto dto = AuthorDto.builder()
                .name("작가 이름")
                .country("국가")
                .build();
        return dto;
    }

    public AuthorDto givenDtoWithId(){
        AuthorDto dto = givenDtoWithoutId();
        dto.setId(999L);
        return dto;
    }

    public Author givenAuthorWithoutId(){
        Author author = Author.builder()
                .name("작가 이름")
                .country("국가")
                .build();
        return author;
    }

    public Author givenAuthorWithId(){
        Author author = Author.builder()
                .name("작가 이름")
                .country("국가")
                .id(999L)
                .build();
        return author;
    }

    public BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .book(book)
                .author(author)
                .build();
        return bookAndAuthor;
    }

    public List<BookAndAuthor> givenBookAndAuthorListWithId() {
        List<BookAndAuthor> lst = new ArrayList<>();
        for(int i = 0; i < 5 ; i ++){
            Author author = givenAuthorWithId();
            Book book = givenBookWithId();
            lst.add(BookAndAuthor.builder().author(author).book(book).id(Long.valueOf(i)).build());
        }
        return lst;
    }

    public BookAndAuthor givenBookAndAuthorWithoutId(){
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .book(givenBookWithId())
                .author(givenAuthorWithId())
                .build();
        return bookAndAuthor;
    }

    public BookAndAuthor givenBookAndAuthorWithId(){
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .book(givenBookWithId())
                .author(givenAuthorWithId())
                .id(999L)
                .build();
        return bookAndAuthor;
    }

    public Book givenBookWithoutId(){
        Book book = Book.builder()
                .category("카테고리")
                .name("이름")
                .build();
        return book;
    }

    public Book givenBookWithId(){
        Book book = Book.builder()
                .category("카테고리")
                .name("이름")
                .id(999L)
                .build();
        return book;
    }


}
