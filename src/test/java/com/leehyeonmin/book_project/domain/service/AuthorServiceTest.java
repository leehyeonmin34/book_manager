package com.leehyeonmin.book_project.domain.service;


import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.serviceImpl.AuthorServiceImpl;
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

import java.util.Optional;

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

//    @BeforeEach
//    public void init(){
//
//        Book book1 = givenBook("책1", "카테고리1");
//        Book book2 = givenBook("책2", "카테고리2");
//        Book book3 = givenBook("책3", "카테고리3");
//
//        Author author1 = givenAuthor("작가1", "나라1");
//        Author author2 = givenAuthor("작가2", "나라2");
//
//        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book1, author1);
//        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book1, author2);
//        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book2, author1);
//        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book2, author2);
//        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book3, author1);
//        BookAndAuthor bookAndAuthor6 = givenBookAndAuthor(book3, author2);
//        //책1 - 작가1,작가2
//        //책2 - 작가1,작가2
//        //책3 - 작가1,작가2
//    }

    @Test
    @DisplayName("author 추가 테스트")
    public void addTest(){

        //given
        AuthorDto authorDto = AuthorDto.builder()
                .name("작가 이름")
                .country("국가")
                .build();
        Author mockAuthor = Author.builder()
                .name(authorDto.getName())
                .country(authorDto.getCountry())
                .id(Long.valueOf(999))
                .build();
        when(authorRepository.save(any(Author.class))).thenReturn(mockAuthor);


        // when
        AuthorDto result =  authorService.addAuthor(authorDto);

        // then
        assertThat(result.getName()).isEqualTo(authorDto.getName());
        assertThat(result.getCountry()).isEqualTo(authorDto.getCountry());
        assertThat(result.getId()).isEqualTo(mockAuthor.getId());
    }

    @Test
    @DisplayName("author 제거 테스트")
    public void removeTest(){
        // GIVEN
        AuthorDto authorDto = AuthorDto.builder()
                .name("작가 이름")
                .country("국가")
                .build();
        Author mockAuthor = Author.builder()
                .name(authorDto.getName())
                .country(authorDto.getCountry())
                .id(Long.valueOf(999))
                .build();
        when(authorRepository.save(any(Author.class))).thenReturn(mockAuthor);
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mockAuthor));

        //when
        Author saved = authorRepository.save(ToEntity.from(authorDto));
        Boolean result = authorService.removeAuthor(saved.getId());
        System.out.println(saved.getId());

        //then
        verify(authorRepository, times(1)).deleteById(any(Long.class));
        assertThat(result).isEqualTo(true);
    }

    public Book givenBook(String name, String category){
        Book book = Book.builder()
                .name(name)
                .category(category)
                .build();
        return bookRepository.save(book);
    }

    public Author givenAuthor(String name, String country){
        Author author = Author.builder()
                .name(name)
                .country(country)
                .build();
        return authorRepository.save(author);
    }

    public BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .book(book)
                .author(author)
                .build();
        return bookAndAuthorRepository.save(bookAndAuthor);
    }

}
