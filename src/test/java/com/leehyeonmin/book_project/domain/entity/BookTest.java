package com.leehyeonmin.book_project.domain.entity;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import io.swagger.annotations.Authorization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class BookTest {

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

    @BeforeEach
    public void beforeEach(){
        createEntities();
    }

    private Long bookId;
    private Long publisherId;
    private Long author1Id;
    private Long author2Id;

    @AfterEach
    public void afterEach(){
        bookAndAuthorRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    private void createEntities(){
        Book book = Book.builder()
                .name("이름")
                .build();
        Publisher publisher = Publisher.builder()
                .name("이름")
                .build();
        Author author1 = Author.builder()
                .name("이름")
                .build();
        Author author2 = Author.builder()
                .name("이름")
                .build();

        Book savedBook = bookRepository.save(book);
        Publisher savedPublisher = publisherRepository.save(publisher);
        Author savedAuthor1 = authorRepository.save(author1);
        Author savedAuthor2 = authorRepository.save(author2);


        bookId = savedBook.getId();
        publisherId = savedPublisher.getId();
        author1Id = savedAuthor1.getId();
        author2Id = savedAuthor1.getId();


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
        Publisher publisher = publisherRepository.getById(publisherId);

        // when
        book.updateBasicInfo("다른 이름", "다른 카테고리"); // 정보 수정

        // then
        Book bookOnDB = bookRepository.getById(book.getId());
        Publisher publisherOnDB = publisherRepository.getById(publisher.getId());

        assertThat(bookOnDB.getName()).isEqualTo("다른 이름");
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
    }

    @Test
    @DisplayName("updateBasicInfo - 새 엔티티 (비영속)")
    public void updateBasicInfo2(){
        // given
        Book book = Book.builder().name("새 책").build();
        Publisher publisher = publisherRepository.getById(publisherId);

        // when
        book.updatePublisher(publisher);
        book.updateBasicInfo("다른 이름", "다른 카테고리"); // 정보 수정
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
        book.updateBasicInfo("다른 이름", "다른 카테고리"); // 정보 수정
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
        Author author2 = authorRepository.getById(author2Id);
        Book book = bookRepository.getById(bookId);
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().book(book).author(author1).build();

        book.addBookAndAuthor(bookAndAuthor);
        author1.addBookAndAuthor(bookAndAuthor);
        BookAndAuthor BAA_DB = bookAndAuthorRepository.save(bookAndAuthor);

        // when
        book.getBookAndAuthors().remove(bookAndAuthor);
        author1.getBookAndAuthors().remove(bookAndAuthor);
        bookAndAuthorRepository.deleteById(BAA_DB.getId());



        // then
        Book bookDB = bookRepository.getById(bookId);
        Author authorDB = authorRepository.getById(author1Id);


        assertThat(bookDB.getBookAndAuthors().contains(bookAndAuthor)).isFalse();
        assertThat(authorDB.getBookAndAuthors().contains(bookAndAuthor)).isFalse();
        assertThat(bookDB.getBookAndAuthors().size()).isEqualTo(0);
        assertThat(authorDB.getBookAndAuthors().size()).isEqualTo(0);
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(0);
//        assertThat(BAA_DB).isNotNull();
    }

}
