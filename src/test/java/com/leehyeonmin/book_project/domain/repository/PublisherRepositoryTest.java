package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublisherRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    private Long publisherId;
    private Long bookId;

    @BeforeEach
    private void beforeEach(){
        Book book = Book.builder()
                .name("이름")
                .build();
        Publisher publisher = Publisher.builder()
                .name("이름")
                .build();
        Book savedBook = bookRepository.save(book);
        Publisher savedPublisher = publisherRepository.save(publisher);
        savedBook.updatePublisher(publisher);

        bookId = savedBook.getId();
        publisherId = savedPublisher.getId();
    }

    @AfterEach
    private void afterEach(){
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("addBook")
    public void addPublisherTest(){
        // given
        Book book = bookRepository.getById(bookId);
        Publisher publisher = publisherRepository.getById(bookId);
        Book newBook = bookRepository.save(Book.builder().name("새 책").build());

        // when
        publisher.addBooks(newBook); // 편의메서드로 새 책 추가

        // then - 연관 book에도 해당내용 적용됨
        Book bookOnDB = bookRepository.getById(newBook.getId());
        Publisher publisherOnDB = publisherRepository.getById(publisherId);
        assertThat(bookOnDB.getPublisher().getName()).isEqualTo(publisher.getName());
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
    }

    @Test
    @DisplayName("removeBook")
    public void removePublisherTest(){
        // given
        Book book = bookRepository.getById(bookId);
        Publisher publisher = publisherRepository.getById(publisherId);
        publisher.addBooks(book);

        // when
        book.updatePublisher(null);
        publisherRepository.delete(publisher);

        // then
        Book bookOnDB = bookRepository.getById(bookId);
        assertThat(bookOnDB).isNotNull();
        assertThat(bookOnDB.getPublisher()).isNull();
        assertThat(publisherRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("updateBasicInfo 성공")
    public void updateBasicInfoSuccess(){
        // given
        Book book = bookRepository.getById(bookId);
        Publisher publisher = publisherRepository.getById(publisherId);
        String changedName = "다른 이름";

        // when
        publisher.updateBasicInfo(changedName);

        // then
        Publisher publisherDB = publisherRepository.getById(publisherId);
        Book bookDB = bookRepository.getById(bookId);
        assertThat(publisherDB.getName()).isEqualTo(changedName);
        assertThat(bookDB.getPublisher().getName()).isEqualTo(changedName);
    }




}
