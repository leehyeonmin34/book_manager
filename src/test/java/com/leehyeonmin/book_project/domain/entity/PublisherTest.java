package com.leehyeonmin.book_project.domain.entity;

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
public class PublisherTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Test
    @DisplayName("updatePublisher - publisher 없었을 때")
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
        Book bookOnDB = bookRepository.getById(savedBook.getId());
        Publisher publisherOnDB = publisherRepository.getById(savedPublisher.getId());
        // then
        assertThat(bookOnDB.getPublisher()).isNotNull();
        assertThat(bookOnDB.getPublisher().getName()).isEqualTo(publisher.getName());
        assertThat(publisherOnDB.getBooks().contains(bookOnDB));
    }
}
