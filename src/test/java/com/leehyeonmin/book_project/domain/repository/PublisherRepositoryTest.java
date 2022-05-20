package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import lombok.Data;
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

    @Test
    @DisplayName("addBook")
    public void updatePublisherTest(){
        // given
        Book book = Book.builder()
                .name("이름")
                .build();
        Publisher publisher = Publisher.builder()
                .name("이름")
                .build();
        Book savedBook = bookRepository.save(book);
        Publisher savedPublisher = publisherRepository.save(publisher);

        // when
        savedPublisher.addBooks(savedBook);

        // then
        Book bookOnDB = bookRepository.getById(savedBook.getId());
        Publisher publisherOnDB = publisherRepository.getById(savedPublisher.getId());
        assertThat(bookOnDB.getPublisher()).isNotNull();
        assertThat(bookOnDB.getPublisher().getName()).isEqualTo(publisher.getName());
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
    }

    @Test
    @DisplayName("removeBook")
    public void removePublisherTest(){
        // given
        Book book = Book.builder()
                .name("이름")
                .build();
        Publisher publisher = Publisher.builder()
                .name("이름")
                .build();
        Book savedBook = bookRepository.save(book);
        Publisher savedPublisher = publisherRepository.save(publisher);
        savedPublisher.addBooks(savedBook);

        // when
        savedBook.updatePublisher(null);
        publisherRepository.delete(savedPublisher);

        // then
        Book bookOnDB = bookRepository.getById(savedBook.getId());
        assertThat(bookOnDB).isNotNull();
        assertThat(bookOnDB.getPublisher()).isNull();
        assertThat(publisherRepository.findAll().size()).isEqualTo(0);
    }




}
