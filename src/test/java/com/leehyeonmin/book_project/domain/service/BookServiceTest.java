package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.dto.ReviewDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.request.AddBookRequest;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.serviceImpl.BookServiceImpl;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    private ReviewRepository reviewRepository;

    @Mock
    private ToEntity toEntity;

    @Mock
    private ToDto toDto;

    @Mock
    private RepoUtils repoUtils;

    @Spy
    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("book 리스트 Success - empty list")
    public void getAllBooksSuccessEmptyList(){
        //given
        lenient().doReturn(Collections.<BookDto>emptyList()).when(bookRepository).findAll();

        // when
        BooksResponse result = bookService.getAllBooks();

        // then
        assertThat(result.getBooks().size()).isEqualTo(0);
        assertThat(result.getBooks()).isNotNull();
        assertThat(result.getTotal()).isEqualTo(0);
    }

    @Test
    @DisplayName("book 리스트 Success - not empty list")
    public void getAllBooksSuccessNotEmptyList(){
        //given
        List<Book> lst = givenBookList();
        lenient().doReturn(lst).when(bookRepository).findAll();

        // when
        BooksResponse result = bookService.getAllBooks();

        // then
        assertThat(result.getBooks()).isNotNull();
        assertThat(result.getBooks().size()).isEqualTo(lst.size());
        assertThat(result.getTotal()).isEqualTo(lst.size());
    }

    @Test
    @DisplayName("book 추가 Failure - Publisher Entity Not Found")
    public void addBookTestFail(){
        BookDto bookDto = bookDtoIn();

        lenient().when(repoUtils.getOneElseThrowException(any(PublisherRepository.class), any(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> bookService.addBook(bookDto)).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("book 추가 Success")
    public void addBookTestSuccess(){
        //given
        BookDto bookDto = bookDtoIn();
        bookDto.setPublisherId(999L);
        bookDto.setAuthorId(999L);
        Publisher publisherOut = publisherOut();

        BookReviewInfo bookReviewInfoIn = BookReviewInfo.builder().build();
        BookReviewInfo bookReviewInfoOut = BookReviewInfo.builder().id(999L).build();

        BookDto finalResult = BookDto.builder()
                .name(bookDto.getName())
                .category(bookDto.getCategory())
                .bookReviewInfoId(bookReviewInfoOut.getId())
                .publisherId(publisherOut.getId())
                .build();

        lenient().when(repoUtils.getOneElseThrowException(any(PublisherRepository.class), any(Long.class))).thenReturn(publisherOut);
        lenient().when(repoUtils.getOneElseThrowException(any(AuthorRepository.class), any(Long.class))).thenReturn(authorOut());
        lenient().when(bookRepository.save(any(Book.class))).thenReturn(
                Book.builder() //dto 토대로 저장할 entity 생성
                        .name(bookDto.getName())
                        .category(bookDto.getCategory())
                        .bookReviewInfo(bookReviewInfoIn)
                        .publisher(publisherOut)
                        .id(999L)
                        .build()
        );
        lenient().when(bookAndAuthorRepository.save(any(BookAndAuthor.class))).thenReturn(bookAndAuthorOut());
        lenient().when(bookRepository.getById(any(Long.class))).thenReturn(
                Book.builder()
                        .name(bookDto.getName())
                        .category(bookDto.getCategory())
                        .bookReviewInfo(bookReviewInfoOut) // db에 저장됨
                        .publisher(publisherOut)
                        .bookAndAuthors(List.of(bookAndAuthorOut())) // getById 시에 bookAndAuthor 추가됨
                        .build()
        );

        lenient().when(toDto.from(any(Book.class))).thenReturn(finalResult);

        //when
        BookDto result = bookService.addBook(bookDto);
//        assertThatCode(() -> bookService.addBook(bookDto)).doesNotThrowAnyException();


        //then
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookAndAuthorRepository, times(1)).save(any(BookAndAuthor.class));
        assertThat(result).as("result").isNotNull();


        //get이랑 find 차이
    }

    @Test
    @DisplayName("book의 publisher 수정 Success")
    public void changePublisherOfBookTest(){
        // given
        Long bookId = 999L;
        Long publisherId = 999L;

        lenient().when(repoUtils.getOneElseThrowException(any(BookRepository.class), any(Long.class))).thenReturn(bookOut());
        lenient().when(repoUtils.getOneElseThrowException(any(PublisherRepository.class), any(Long.class))).thenReturn(publisherOut());
        lenient().when(bookRepository.getById(any(Long.class))).thenReturn(bookOut());
        lenient().when(toDto.from(any(Book.class))).thenReturn(bookDtoOut());

        // when
        BookDto saved = bookService.changePublisherOfBook(bookId, publisherId);

        // then
        assertThat(saved).isNotNull();
    }


    @Test
    @DisplayName("book 삭제 Success")
    public void removeBookTestSuccess(){
        // given
        lenient().when(bookRepository.existsById(any(Long.class))).thenReturn(true);
//        lenient().when(bookRepository.deleteById(any(Long.class))).doNothing();

        // when
        bookService.removeBook(999L);

        // then
        verify(bookRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("book 삭제 Fail - invalid bookId")
    public void removeBookTestFail(){
        // given
        lenient().when(bookRepository.existsById(any(Long.class))).thenReturn(false);

        // when - then
        assertThatThrownBy(() -> bookService.removeBook(999L)).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    @DisplayName("book 모든 리뷰 가져오기 Fail")
    public void getAllReviewsTestFail(){
        // given
        lenient().when(bookRepository.existsById(any(Long.class))).thenReturn(false);

        // when - then
        assertThatThrownBy(() -> bookService.getAllReviews(999L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("book 모든 리뷰 가져오기 Success")
    public void getAllReviewsTestSuccess(){
        // given
        List<Review> lst = givenReviewList();
        lenient().when(bookRepository.existsById(any(Long.class))).thenReturn(true);
        lenient().when(reviewRepository.getByBookId(999L)).thenReturn(lst);

        // when
        List<ReviewDto> result = bookService.getAllReviews(999L);

        // then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(lst.size());

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
        Author author = Author.builder()
                .country("국가")
                .name("이름")
                .id(999L)
                .build();
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

    private BookAndAuthor bookAndAuthorOut(){
        BookAndAuthor entity = BookAndAuthor.builder()
                .author(authorOut())
                .book(bookOut())
                .build();
        return entity;
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

    private List<Review> givenReviewList(){
        List<Review> lst = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Review review = Review.builder()
                    .content("내용")
                    .id(999L)
                    .build();
            lst.add(review);
        }
        return lst;
    }






}
