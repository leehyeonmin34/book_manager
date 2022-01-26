package com.leehyeonmin.book_project.domain;

import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class BookAndAuthorTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Test
    @Transactional
    public void test() {
        Book book1 = givenBook("책1", "카테고리1");
        Book book2 = givenBook("책2", "카테고리2");
        Book book3 = givenBook("책3", "카테고리3");
        Book book4 = givenBook("책4", "카테고리4");

        Author author1 = givenAuthor("작가1", "나라1");
        Author author2 = givenAuthor("작가2", "나라2");

        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book1, author1);
        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book1, author2);
        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book2, author1);
        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book2, author2);
        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book4, author1);
        BookAndAuthor bookAndAuthor6 = givenBookAndAuthor(book4, author2);

//        book1.addBookAndAuthor(bookAndAuthor1);
//        book2.addBookAndAuthor(bookAndAuthor2);
//        book3.addBookAndAuthor(bookAndAuthor3, bookAndAuthor4);
//        book4.addBookAndAuthor(bookAndAuthor5, bookAndAuthor6);
//
//        author1.addBookAndAuthor(bookAndAuthor1, bookAndAuthor2, bookAndAuthor3);
//        author2.addBookAndAuthor(bookAndAuthor2, bookAndAuthor4, bookAndAuthor6);

        List<BookAndAuthor> bookAndAuthors = authorRepository.findById(1L).get().getBookAndAuthors();
        bookAndAuthors.forEach(System.out::println);

    }

    public Book givenBook(String name, String category){
        Book book = new Book();
        book.setName(name);
        book.setCategory(category);
        return bookRepository.save(book);
    }

    public Author givenAuthor(String name, String country){
        Author author = new Author();
        author.setName(name);
        author.setCountry(country);
        return authorRepository.save(author);
    }

    public BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = new BookAndAuthor();
        bookAndAuthor.setBook(book);
        bookAndAuthor.setAuthor(author);
        return bookAndAuthorRepository.save(bookAndAuthor);
    }

}
