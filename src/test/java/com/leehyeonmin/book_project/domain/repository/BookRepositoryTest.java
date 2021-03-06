package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.Enum.Category;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    EntityManager em;


//    @Autowired
//    ToDto toDto;

    @BeforeEach
    public void beforeEach(){
        createEntities();
    }


    private Long bookId;
    private Long publisherId;
    private Long author1Id;
    private Long author2Id;
    private Long bookAndAuthorId;

    @AfterEach
    public void afterEach(){
        bookAndAuthorRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    private void createEntities(){
        Book book = Book.builder()
                .name("책")
                .build();
        Publisher publisher = Publisher.builder()
                .name("출판사")
                .build();
        Author author1 = Author.builder()
                .name("작가1")
                .build();
        Author author2 = Author.builder()
                .name("작가2")
                .build();
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();

        //각 엔티티 DB에 저장 및 연관관계 설정
        Book savedBook = bookRepository.save(book);
        Publisher savedPublisher = publisherRepository.save(publisher);
        Author savedAuthor1 = authorRepository.save(author1);
        Author savedAuthor2 = authorRepository.save(author2);
        bookAndAuthor.updateBook(book);
        bookAndAuthor.updateAuthor(author1);
        BookAndAuthor savedBookAndAuthor = bookAndAuthorRepository.save(bookAndAuthor);

        bookId = savedBook.getId();
        publisherId = savedPublisher.getId();
        author1Id = savedAuthor1.getId();
        author2Id = savedAuthor1.getId();
        bookAndAuthorId = savedBookAndAuthor.getId();


    }

    @Test
    @DisplayName("findByCategory(Pageable) - 성공")
    public void findByCategoryTest(){
        // given
        Category category = Category.ART;
        int pageNum = 1;
        int size = 4;
        int total = 10;
        List<Book> books = new ArrayList<>();
        for(int i = 0 ; i < total; i ++) {
            Book book = Book.builder().category(category).id(Long.valueOf(i * 10)).build();
            bookRepository.save(book);
            books.add(book);
        }
        PageRequest page = PageRequest.of(0, size);
        PageRequest page2 = PageRequest.of(total / size , size);

        // when
        Page<Book> result = bookRepository.findByCategory(category, page);
        Page<Book> result2 = bookRepository.findByCategory(category, page2);

        // then
        assertThat(result.getContent().get(0).getCategory()).isEqualTo(category);
        assertThat(result2.getContent().size()).isEqualTo(total % size);

        assertThat(result.getTotalElements()).isEqualTo(total);
    }

    @Test
    @DisplayName("updatePublisher - publisher 없었을 때")
    public void updatePublisherTest(){
        // given
        Book book = bookRepository.getById(bookId);
        Publisher publisher = publisherRepository.getById(publisherId);

        // when
        book.updatePublisher(publisher);

        // then
        Book bookOnDB = bookRepository.getById(book.getId());
        Publisher publisherOnDB = publisherRepository.getById(publisher.getId());

        assertThat(bookOnDB.getPublisher()).isNotNull();
        assertThat(bookOnDB.getPublisher().getName()).isEqualTo(publisher.getName());
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
    }

    @Test
    @DisplayName("updatePublisher - publisher 있었을 때")
    public void updatePublisherTest2(){
        // given
        Book book = bookRepository.getById(bookId);
        Publisher publisher = publisherRepository.getById(publisherId);
        Publisher newPub = publisherRepository.save(
                Publisher.builder()
                        .name("새 출판사")
                        .build()
        );

        // when
        book.updatePublisher(publisher); // 출판사 처음 매칭
        book.updatePublisher(newPub); // 출판사 변경

        // then
        Book bookOnDB = bookRepository.getById(book.getId());
        Publisher oldPubOnDB = publisherRepository.getById(publisher.getId());
        Publisher newPubOnDB = publisherRepository.getById(newPub.getId());

        assertThat(bookOnDB.getPublisher().getName()).isEqualTo(newPub.getName());
        assertThat(newPubOnDB.getBooks().contains(bookOnDB));
        assertThat(!oldPubOnDB.getBooks().contains(bookOnDB));

    }

    @Test
    @DisplayName("updateBasicInfo - 기존 엔티티 (영속)")
    public void updateBasicInfo(){
        // given
        Book book = bookRepository.getById(bookId);
        String changedName = "다른 이름";

        // when
        book.updateBasicInfo(changedName, Category.ART); // 정보 수정

        // then
        Book bookOnDB = bookRepository.getById(bookId);
        Publisher publisherOnDB = publisherRepository.getById(publisherId);
        BookAndAuthor bookAndAuthorOnDB = bookAndAuthorRepository.getById(bookAndAuthorId);
        Author authorOnDB = authorRepository.getById(author1Id);

        // 모든 연관 엔티티에서 수정됨
        assertThat(bookOnDB.getName()).isEqualTo(changedName);
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
        assertThat(bookAndAuthorOnDB.getBook().getName()).isEqualTo(changedName);
        assertThat(authorOnDB.getBookAndAuthors().get(0).getBook().getName()).isEqualTo(changedName);
    }

    @Test
    @DisplayName("updateBasicInfo - 새 엔티티 (비영속)")
    public void updateBasicInfo2(){
        // given
        Book book = Book.builder().name("새 책").build();
        Publisher publisher = publisherRepository.getById(publisherId);

        // when
        book.updatePublisher(publisher);
        book.updateBasicInfo("다른 이름", Category.ART); // 정보 수정
        em.persist(book);

        // then
        Book bookOnDB = bookRepository.getById(book.getId());
        Publisher publisherOnDB = publisherRepository.getById(publisher.getId());

        assertThat(bookOnDB.getName()).isEqualTo("다른 이름");
        assertThat(publisherOnDB.getBooks().contains(bookOnDB)).isTrue();
    }

    @Test
    @DisplayName("비영속상태의 새 엔티티 퍼시스트 후 해당 아이디 접근")
    public void updateBasicInfo3(){
        // given
        Book book = Book.builder().name("새 책").build();

        // when
        book.updateBasicInfo("다른 이름", Category.ART); // 정보 수정
        em.persist(book);

        // then
        Book bookOnDB = bookRepository.getById(book.getId());
        assertThat(bookOnDB.getName()).isEqualTo("다른 이름");
    }

    @Test
    @DisplayName("book에 author 추가")
    public void addAuthor(){
        // given
        Author author1 = authorRepository.getById(author1Id);
        Author author2 = authorRepository.getById(author2Id);
        Book book = bookRepository.getById(bookId);
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().book(book).author(author1).build();
//        BookAndAuthor savedBAA = bookAndAuthorRepository.save(bookAndAuthor);

        // when
        book.addBookAndAuthor(bookAndAuthor);
        author1.addBookAndAuthor(bookAndAuthor);
        BookAndAuthor BAA_DB = bookAndAuthorRepository.save(bookAndAuthor);

        // then
        Book bookDB = bookRepository.getById(bookId);
        Author authorDB = authorRepository.getById(author1Id);

        assertThat(bookDB.getBookAndAuthors().contains(bookAndAuthor)).isTrue();
        assertThat(authorDB.getBookAndAuthors().contains(bookAndAuthor)).isTrue();
        assertThat(bookDB.getBookAndAuthors().get(0).getAuthor()).isNotNull();
        assertThat(authorDB.getBookAndAuthors().get(0).getBook()).isNotNull();
        assertThat(BAA_DB).isNotNull();
    }

    @Test
    @DisplayName("book에서 author 제거")
    public void removeAuthor(){
        // given
        Author author1 = authorRepository.getById(author1Id);
        Book book = bookRepository.getById(bookId);
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();

        bookAndAuthor.updateBook(book);
        bookAndAuthor.updateAuthor(author1);
        BookAndAuthor BAA_DB = bookAndAuthorRepository.save(bookAndAuthor);

        bookAndAuthorRepository.findAll().forEach(System.out::println);

        // when
        book.getBookAndAuthors().remove(bookAndAuthor);
        author1.getBookAndAuthors().remove(bookAndAuthor);
        bookAndAuthorRepository.deleteById(BAA_DB.getId());


        // then
        Book bookDB = bookRepository.getById(bookId);
        Author authorDB = authorRepository.getById(author1Id);


        assertThat(bookDB.getBookAndAuthors().contains(bookAndAuthor)).isFalse();
        assertThat(authorDB.getBookAndAuthors().contains(bookAndAuthor)).isFalse();
        assertThat(bookDB.getBookAndAuthors().size()).isEqualTo(1);
        assertThat(authorDB.getBookAndAuthors().size()).isEqualTo(1);
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(1);
//        assertThat(BAA_DB).isNotNull();
    }

    @Test
    @DisplayName("bookService.addBook")
    public void bookServiceAddBook(){

        // given
        Author author = authorRepository.getById(author1Id);
        Publisher publisher = publisherRepository.getById(publisherId);
        BookDto dto = BookDto.builder()
                .name("책 이름")
                .categoryCode(Category.ART.getCode())
                .categoryName(Category.ART.getDesc())
                .publisherId(publisherId)
                .authors(List.of(new AuthorDto(author)))
                .build();

        // when

        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();

        // 연관관계 필요 없는 정보 바로 셋
        Book book = Book.builder()
                .name(dto.getName())
                .category(Category.ofCode(dto.getCategoryCode()))
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();
        bookRepository.save(book);

        // 연관관계 필요한 정보 설정
        book.updatePublisher(publisher);
        publisher.addBooks(book);

        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
        bookAndAuthor.updateBook(book);
        bookAndAuthor.updateAuthor(author);
        bookAndAuthorRepository.save(bookAndAuthor);
        bookRepository.save(book);


        // then
        assertThatCode(() -> bookRepository.getById(book.getId())).doesNotThrowAnyException();
    }


}