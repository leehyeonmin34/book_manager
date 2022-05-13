package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import jdk.jfr.Name;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    EntityManager em;

    @Test
    public void AuthorRepositoryTestIsNotNull(){
        assertThat(authorRepository).isNotNull();
    }

    @BeforeEach
    public void beforeEach(){
        createBookAuthorBookAndAuthorAndConnet();
    }

    private void createBookAuthorBookAndAuthorAndConnet(){

        Book book = Book.builder()
                .name("이름")
                .build();
        Author author = Author.builder()
                .name("이름")
                .build();
        Book bookSaved = bookRepository.save(book);
        Author authorSaved = authorRepository.save(author);

        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .author(authorSaved)
                .book(bookSaved)
                .build();
        BookAndAuthor saved = bookAndAuthorRepository.save(bookAndAuthor);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("author modify (성공)")
    public void authorModify(){

        //given
        Author existingAuthor = authorRepository.findAll().get(0);

        //when
        existingAuthor.updateBasicInfo("다른 이름", "다른 나라");
        Author modified = authorRepository.save(existingAuthor);
        // author만 수정하면

        // (flush 전부터) author의 bookAndAuthor에도 적용된다.
        assertThat(modified.getBookAndAuthors().get(0).getAuthor().getName()).isEqualTo(existingAuthor.getName());
        em.flush();
        em.clear();
        assertThat(modified.getBookAndAuthors().get(0).getAuthor().getName()).isEqualTo(existingAuthor.getName());
        assertThat(bookAndAuthorRepository.findAll().get(0).getAuthor().getName()).isEqualTo(existingAuthor.getName());
    }

    @Test
    @DisplayName("author delete (성공)")
    public void authorDelete(){
        //given
        Author existingAuthor = authorRepository.findAll().get(0);
        authorRepository.delete(existingAuthor);
        // author만 수정하면

        // (flush 전부터) author의 bookAndAuthor에도 적용된다.
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(0);
    }

}
