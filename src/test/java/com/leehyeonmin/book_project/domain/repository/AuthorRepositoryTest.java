package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private Long bookId;
    private Long authorId;
    private Long bookAndAuthorId;

    @Test
    public void AuthorRepositoryTestIsNotNull(){
        assertThat(authorRepository).isNotNull();
    }

    @BeforeEach
    public void beforeEach(){
        createBookAuthorBookAndAuthorAndConnect();
    }

    private void createBookAuthorBookAndAuthorAndConnect(){

        Book book = Book.builder()
                .name("이름")
                .build();
        Author author = Author.builder()
                .name("이름")
                .build();
        Book savedBook = bookRepository.save(book);
        Author savedAuthor = authorRepository.save(author);

        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
        bookAndAuthor.updateBook(savedBook);
        bookAndAuthor.updateAuthor(savedAuthor);
        BookAndAuthor savedBookAndAuthor = bookAndAuthorRepository.save(bookAndAuthor);

        bookId = savedBook.getId();
        authorId = savedAuthor.getId();
        bookAndAuthorId = savedBookAndAuthor.getId();
    }


    @Test
    @DisplayName("author modify (성공)")
    public void authorModify(){

        //given
        Author author = authorRepository.getById(authorId);

        //when
        author.updateBasicInfo("다른 이름", "다른 나라");
        Author savedAuthor = authorRepository.save(author);

        // then - author와 연관관계에서 변경내용 확인
        assertThat(savedAuthor.getBookAndAuthors().get(0).getAuthor().getName()).isEqualTo(savedAuthor.getName());
        assertThat(bookAndAuthorRepository.findAll().get(0).getAuthor().getName()).isEqualTo(savedAuthor.getName());
    }



    @Test
    @DisplayName("author delete (성공)")
    public void authorDelete(){
        //given
        Author author = authorRepository.getById(authorId);

        // when - 연관된 entity들을 지우거나 관계를 끊어낸 후 자신을 삭제한다.
        bookAndAuthorRepository.deleteAll(author.getBookAndAuthors());
        authorRepository.delete(author);
        em.flush(); // delete는 바로 삭제되지 않고 요청만 전달되기 때문에
        em.clear(); // 테스트 메서드 내 확인을 위해 flush로 DB에 반영하는 과정이 필요하다.

        // then - 연관된 bookAndAuthor와 book에서 author가 삭제됨
        Book book = bookRepository.getById(bookId);
        assertThat(book.getBookAndAuthors().size()).isEqualTo(0);
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(0);
        assertThat(authorRepository.findById(authorId).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("updateBasicInfo(성공)")
    public void authorUpdateSuccess(){
        // given
        Author author = authorRepository.getById(authorId);
        Book book = bookRepository.getById(bookId);

        // when - 영속화한 연관 속성들을 넣어주고, 자신을 저장하면 연관관계가 잘 적용된다.
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
        bookAndAuthor.updateAuthor(author);
        bookAndAuthor.updateBook(book);
        bookAndAuthorRepository.save(bookAndAuthor);


        // then
        em.flush();
        em.clear();
        Author authorDB = authorRepository.getById(authorId);
        Book bookDB = bookRepository.getById(bookId);

        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(2);
        assertThat(bookDB.getBookAndAuthors().size()).isEqualTo(2);
        assertThat(authorDB.getBookAndAuthors().size()).isEqualTo(2);
    }



}
