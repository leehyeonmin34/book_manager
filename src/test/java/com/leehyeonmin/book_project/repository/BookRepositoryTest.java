package com.leehyeonmin.book_project.repository;

import com.leehyeonmin.book_project.domain.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthorRepository authorRepository;



    @Test
    @DisplayName("Book 저장")
    public void BookRepositorySaveTest(){
        //given
        Author author = Author.builder().name("작가이름").build();
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).build();
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        bookAndAuthors.add(bookAndAuthor);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .bookAndAuthors(bookAndAuthors) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();

        // when
        Book result = bookRepository.save(book);
        author.getBookAndAuthors().add(bookAndAuthor);
        Author result2 = authorRepository.save(author);

        //then
        assertThat(result.getBookReviewInfo().getId()).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCategory()).isEqualTo("카테고리");
        assertThat(result.getName()).isEqualTo("이름");
        assertThat(result.getPublisher().getId()).isNotNull();
        assertThat(result.getPublisher().getName()).isEqualTo("출판사");
        assertThat(result.getBookAndAuthors().get(0).getId()).isNotNull();
        assertThat(result.getBookAndAuthors().get(0).getAuthor().getName()).isEqualTo("작가이름");
        Long authorId = result.getBookAndAuthors().get(0).getAuthor().getId();
        assertThat(result2.getBookAndAuthors().get(0).getId()).isEqualTo(result.getBookAndAuthors().get(0).getId());
        assertThat(result.getStatus().getDescription()).isEqualTo("대여 가능");
    }

    @Test
    @DisplayName("Book 삭제 Success")
    public void BookRepositoryRemoveSuccessTest(){

        //given
        Author author = Author.builder().name("작가이름").build();
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).build();
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        bookAndAuthors.add(bookAndAuthor);
        Review review = Review.builder().content("리뷰내용").score(4.5f).build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .bookAndAuthors(bookAndAuthors) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .reviews(reviews)
                .build();

        // when
        Book result = bookRepository.save(book);
        bookRepository.deleteById(result.getId());


        //then
        assertThat(bookRepository.findById(result.getId()).isPresent()).isEqualTo(false);
        assertThat(bookReviewInfoRepository.findById(result.getBookReviewInfo().getId()).isPresent()).isEqualTo(false);
        assertThat(bookAndAuthorRepository.findById(result.getBookAndAuthors().get(0).getId()).isPresent()).isEqualTo(false);
        assertThat(publisherRepository.findById(result.getPublisher().getId()).isPresent()).isEqualTo(true);
        assertThat(publisherRepository.findById(result.getPublisher().getId()).get().getBooks().size()).isEqualTo(0);
        assertThat(reviewRepository.findById(result.getReviews().get(0).getId()).isPresent()).isEqualTo(false);


    }

    @Test
    @DisplayName("Book 수정 Success")
    public void BookRepositoryUpdateSuccessTest() {

        //given
        Author author = Author.builder().name("작가이름").build();
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).build();
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        bookAndAuthors.add(bookAndAuthor);
        Review review = Review.builder().content("리뷰내용").score(4.5f).build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .bookAndAuthors(bookAndAuthors) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .reviews(reviews)
                .build();

        // when
        Book saved = bookRepository.save(book);
        saved.getReviews().get(0).setContent("리뷰 변경");
        saved.setName("다른 이름");

        Book result = bookRepository.save(saved);

        // then
        assertThat(result.getId()).isEqualTo(saved.getId());
        assertThat(result.getName()).isEqualTo("다른 이름");
        assertThat(result.getReviews().get(0).getContent()).isEqualTo("리뷰 변경");
        assertThat(reviewRepository.getById(saved.getReviews().get(0).getId()).getContent()).isEqualTo("리뷰 변경");
    }
}
