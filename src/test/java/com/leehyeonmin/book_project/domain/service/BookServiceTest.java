package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.request.AddBookRequest;
import com.leehyeonmin.book_project.domain.serviceImpl.BookServiceImpl;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ToEntity toEntity;

    @Mock
    private ToDto toDto;

    @Test
    @DisplayName("book 추가(author, publisher 생성) Success")
    public void addBookWithoutExistingMemberTest(){
        //given
        BookDto bookDtoIn = bookDtoIn();
        AuthorDto authorDto = AuthorDto.builder()
                .name("이름")
                .country("국가")
                .build();
        PublisherDto publisherDto = PublisherDto.builder()
                .name("이름")
                .build();
        AddBookRequest request = AddBookRequest.builder().bookDto(bookDtoIn).authorDto(authorDto).publisherDto(publisherDto).build();

        lenient().when(toEntity.from(any(AuthorDto.class))).thenReturn(authorIn());
        lenient().when(toEntity.from(any(PublisherDto.class))).thenReturn(publisherIn());
        lenient().when(authorRepository.getById(any(Long.class))).thenReturn(authorOut());
        lenient().when(publisherRepository.getById(any(Long.class))).thenReturn(publisherOut());
        lenient().when(bookRepository.save(any(Book.class))).thenReturn(bookOut());
        lenient().when(toDto.from(any(Book.class))).thenReturn(bookDtoOut());

        //when
        BookDto result = bookService.addBook(request);

        //then
        verify(toEntity, times(1)).from(any(AuthorDto.class));
        verify(toEntity, times(1)).from(any(PublisherDto.class));
        verify(bookRepository, times(1)).save(any(Book.class));
        //get이랑 find 차이

    }

    @Test
    @DisplayName("book 추가(author, publisher 로드) Success")
    public void addBookWithExistingMemeberTest(){
        //given
        BookDto bookDto = bookDtoIn();
        bookDto.setPublisherId(999L);
        bookDto.setAuthorId(999L);
        AddBookRequest request = AddBookRequest.builder().bookDto(bookDto).authorDto(null).publisherDto(null).build();

        lenient().when(toEntity.from(any(AuthorDto.class))).thenReturn(authorIn());
        lenient().when(toEntity.from(any(PublisherDto.class))).thenReturn(publisherIn());
        lenient().when(authorRepository.getById(any(Long.class))).thenReturn(authorOut());
        lenient().when(publisherRepository.getById(any(Long.class))).thenReturn(publisherOut());
        lenient().when(bookRepository.save(any(Book.class))).thenReturn(bookOut());
        lenient().when(toDto.from(any(Book.class))).thenReturn(bookDtoOut());

        //when
        BookDto result = bookService.addBook(request);

        //then
        verify(authorRepository, times(1)).getById(any(Long.class));
        verify(publisherRepository, times(1)).getById(any(Long.class));
        verify(bookRepository, times(1)).save(any(Book.class));
        //get이랑 find 차이
    }

    @Test
    @DisplayName("book 삭제 Success")
    public void removeBookTest(){

    }

    @Test
    @DisplayName("book 수정 Success")
    public void modifyBookTest(){

    }


    private BookDto bookDtoIn(){
        BookDto bookDto = BookDto.builder()
                .name("책 이름")
                .category("카테고리")
                .build();
        return bookDto;
    }

    private BookDto bookDtoOut(){
        BookDto bookDto = bookDtoIn();
        bookDto.setId(999L);
        return bookDto;
    }

    private Book bookIn(){
        BookDto dto = bookDtoIn();
        Book book = Book.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .build();
        return book;
    }

    private Book bookOut(){
        Book book = Book.builder()
                .id(999L)
                .build();
        return book;
    }

    private Author authorIn(){
        Author author = Author.builder()
                .country("국가")
                .name("이름")
                .build();
        return author;
    }

    private Author authorOut(){
        Author author = authorIn();
        author.setId(999L);
        return author;
    }

    private AuthorDto authorDtoIn(){
        AuthorDto authorDto = AuthorDto.builder()
                .name("이름")
                .country("국가")
                .build();
        return authorDto;
    }

    private AuthorDto authorDtoOut(){
        AuthorDto authorDto = authorDtoIn();
        authorDto.setId(999L);
        return authorDto;
    }

    private PublisherDto publisherDtoIn(){
        PublisherDto dto = PublisherDto.builder()
                .name("이름")
                .build();
        return dto;
    }

    private PublisherDto publisherDtoOut(){
        PublisherDto dto = PublisherDto.builder()
                .name("이름")
                .id(999L)
                .build();
        return dto;
    }

    private Publisher publisherIn(){
        Publisher entity = Publisher.builder()
                .name("이름")
                .build();
        return entity;
    }

    private Publisher publisherOut(){
        Publisher entity = Publisher.builder()
                .name("이름")
                .id(999L)
                .build();
        return entity;
    }







}
