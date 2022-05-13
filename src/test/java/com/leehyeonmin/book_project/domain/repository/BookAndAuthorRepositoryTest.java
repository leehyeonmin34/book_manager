package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class BookAndAuthorRepositoryTest {

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void beforeEach(){
        createBookAuthorBookAndAuthorAndConnet();
    }

    @AfterEach
    public void afterEach(){
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        bookAndAuthorRepository.deleteAll();
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
//        book.addBookAndAuthor(saved);
//        author.addBookAndAuthor(saved);
//        bookRepository.save(book);
//        authorRepository.save(author);
    }

    @Test
    @DisplayName("BookAndAuthorReposiory 추가 성공")
    public void addSuccess(){

        // then
        // bookAndAuthor만 잘 저장해도 book, author는 알아서 저장됨.
        // em.flush를 해야 DB에 적용이 되니까, 주의하자.

        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(1);
        assertThat(bookRepository.findAll().size()).isEqualTo(1);
        assertThat(authorRepository.findAll().size()).isEqualTo(1);
        assertThat(bookRepository.findAll().get(0).getBookAndAuthors().size()).isEqualTo(1);
        assertThat(authorRepository.findAll().get(0).getBookAndAuthors().size()).isEqualTo(1);
        System.out.println(bookRepository.findAll().get(0).getBookAndAuthors());
        System.out.println(authorRepository.findAll().get(0).getBookAndAuthors());
//        assertThat(bookRepository.findAll().get(0).)

    }

    @Test
    @DisplayName("BookAndAuthorReposiory 수정(Book) 성공")
    public void modifySuccess(){
        // given
        Book existingBook = bookRepository.findAll().get(0);
        existingBook.updateBasicInfo("다른 이름", existingBook.getCategory());
//        Book saved = bookRepository.save(existingBook);
//        Author authorSaved = authorRepository.save(author);
        // book 변경된 내용 저장할 필요 없음
        // book, author는 따로 저장할 필요 없음

        // when
        //bookAndAuthor만 잘 저장하고, flush하면 됨.
        existingBook.getBookAndAuthors().stream().forEach(item -> {
                    item.updateBook(existingBook);
                    bookAndAuthorRepository.save(item);
                });

        em.flush();
        em.clear();

        // then
        // bookAndAuthor만 잘 저장해도 book, author는 알아서 저장됨
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(1); // bookAndAuthor 추가된 것 없이 수정만 됨
        assertThat(bookRepository.findAll().size()).isEqualTo(1);
        assertThat(bookRepository.findById(existingBook.getId()).get().getName()).isEqualTo(existingBook.getName()); // book에 잘 적용됨
        assertThat(authorRepository.findAll().get(0).getBookAndAuthors().get(0).getBook().getName()).isEqualTo(existingBook.getName()); // author에도 잘 적용됨
    }



    @Test
    @DisplayName("BookAndAuthorReposiory delete 성공")
    public void deleteSuccess(){

        //when - 책의 bookAndAuthor를 삭제
        Book existingBook = bookRepository.findAll().get(0);
        existingBook.getBookAndAuthors().stream().forEach(item -> {
            bookAndAuthorRepository.deleteById(item.getId());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>> deleted" + item.getId());
        });

        // then - book-author 사이의 연관관계(bookAndAuthor)만 사라지고 book, author는 계속 존재함
        assertThat(bookRepository.findAll().size()).isNotEqualTo(0);
        assertThat(bookAndAuthorRepository.findAll().size()).isEqualTo(0);
        assertThat(authorRepository.findAll().size()).isNotEqualTo(0);
    }


}
