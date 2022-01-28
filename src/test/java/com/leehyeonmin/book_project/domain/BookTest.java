package com.leehyeonmin.book_project.domain;

import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.BookReviewInfoRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class BookTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Test
    public void bookAndBookReviewInfoTest(){

        givenReviewInfo();

        Book book = bookReviewInfoRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBook();

        System.out.println(book);

        BookReviewInfo review = bookRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();

        System.out.println(review);
//        List<Book> books = bookRepository.findAll();
//        List<BookReviewInfo> reviews = bookReviewInfoRepository.findAll();
//
//        books.forEach(System.out::println);
//        reviews.forEach(System.out::println);

    }

    @Test
//    @Transactional
    public void bookAndPublisherTest(){ //ManyToOne에서 CascadeType.ALL 해주면, many에서 수정하면 one에서도 수정되어 DB에 반영
        Publisher publisher = new Publisher();
        publisher.setName("현민출판사");
//        publisherRepository.save(publisher);

        Book book = new Book();
        book.setName("월간 소나무");
//        bookRepository.save(book);

//        publisher.addBooks(book);
        book.setPublisher(publisher);
        book.setStatus(300);

        bookRepository.save(book);
//        publisherRepository.save(publisher);

        Publisher publisherResult = bookRepository.findById(1L).get().getPublisher();
        List<Book> books = publisherRepository.findById(1L).get().getBooks();

        books.forEach(System.out::println);
        System.out.println(publisherResult);
        System.out.println(bookRepository.findRawRecord().values());
    }

    @Test
    public void sofeDeletedTest(){
        Book book1 = givenBook();
        Book book2 = givenBook();

        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }


    public Book givenBook(){
        Book book = new Book();
        book.setName("지적재산과 법");
//        book.setPublisher(2L);
        return bookRepository.save(book);
    }

    public void givenReviewInfo(){
        BookReviewInfo reviewInfo = new BookReviewInfo();
        reviewInfo.setAverageReviewScore(3.2f);
        reviewInfo.setReviewCount(2);
        reviewInfo.setBook(givenBook());
        bookReviewInfoRepository.save(reviewInfo);
    }

    public Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("현민출판사");
        return publisher;
    }

}
