package com.leehyeonmin.book_project.domain.service;


import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.serviceImpl.AuthorServiceImpl;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import jdk.jfr.Name;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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



    @BeforeEach
    public void init(){


    }

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
    @DisplayName("author 추가 (실패)")
    public void addTestFail(){
        // then
        assertThatThrownBy(() -> authorService.addAuthor(null))
                .isInstanceOf(NoEntityException.class);
    }

    @Test
    @DisplayName("author 제거 테스트 (성공)")
    public void removeTestSuccess(){
        // GIVEN
        Author givenAuthorWithId = givenAuthorWithId();
        AuthorDto givenDtoWithId = givenDtoWithId();
        List<BookAndAuthor> givenBookAndAuthorList = givenBookAndAuthorListWithId();

        lenient().when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(givenAuthorWithId));
        lenient().when(bookAndAuthorRepository.getByAuthorId(any(Long.class))).thenReturn(givenBookAndAuthorList);

        //when
        Boolean result = authorService.removeAuthor(givenDtoWithId.getId());

        //then
        verify(authorRepository, times(1)).deleteById(any(Long.class));
        verify(bookAndAuthorRepository, times(givenBookAndAuthorList.size())).delete(any(BookAndAuthor.class));
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("author 제거 테스트 (실패)")
    public void removeTestFail(){
        assertThatThrownBy(() -> authorService.removeAuthor(null))
                .isInstanceOf(NoEntityException.class);
    }

    @Test
    @DisplayName("author 수정 테스트 (성공)")
    public void modifyTest(){
        // GIVEN
        Author givenAuthorWithId = givenAuthorWithId();
        List<BookAndAuthor> givenBookAndAuthorList = givenBookAndAuthorListWithId();

        AuthorDto modifiedAuthorDto = givenDtoWithId();
        modifiedAuthorDto.setCountry("다른 나라");
        Author modifiedAuthor = givenAuthorWithId();
        modifiedAuthor.setCountry(modifiedAuthorDto.getCountry());

        lenient().when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(givenAuthorWithId));
        lenient().when(toEntity.from(any(AuthorDto.class))).thenReturn(modifiedAuthor);
        lenient().when(authorRepository.save(any(Author.class))).thenReturn(modifiedAuthor);
        lenient().when(bookAndAuthorRepository.getByAuthorId(any(Long.class))).thenReturn(givenBookAndAuthorList);
        lenient().when(bookAndAuthorRepository.save(any(BookAndAuthor.class))).thenReturn(givenBookAndAuthorWithId());
        lenient().when(toDto.from(any(Author.class))).thenReturn(modifiedAuthorDto);

        //when
        AuthorDto result = authorService.modifyAuthor(modifiedAuthorDto);

        //then
        assertThat(result.getName()).isEqualTo(modifiedAuthorDto.getName());
        assertThat(result.getCountry()).isEqualTo(modifiedAuthorDto.getCountry());
        assertThat(result.getId()).isEqualTo(modifiedAuthorDto.getId());
        verify(bookAndAuthorRepository, times(givenBookAndAuthorList.size())).save(any(BookAndAuthor.class));
        verify(bookAndAuthorRepository, times(1)).getByAuthorId(any(Long.class));

    }

    @Test
    @DisplayName("author 수정 테스트 (실패)")
    public void modifyTestFail(){
        assertThatThrownBy(() -> authorService.modifyAuthor(null))
                .isInstanceOf(NoEntityException.class);
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
        Author author = givenAuthorWithoutId();
        author.setId(999L);
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
        BookAndAuthor bookAndAuthor = givenBookAndAuthorWithoutId();
        bookAndAuthor.setId(999L);
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
        Book book = givenBookWithoutId();
        book.setId(999L);
        return book;
    }


}
