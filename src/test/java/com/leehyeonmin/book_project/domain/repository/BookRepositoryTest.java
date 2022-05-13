package com.leehyeonmin.book_project.domain.repository;

import com.leehyeonmin.book_project.domain.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.leehyeonmin.book_project.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Book 전부 로드 - 0개 로드")
    public void BookRepositoryFindAllTest(){
        // when
        List<Book> result = bookRepository.findAll();

        // then

        assertThat(result).isEqualTo(Collections.<Book>emptyList());
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    @DisplayName("Book 저장 ")
    public void BookRepositorySaveTest(){
        //given
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        Author authorMock = Author.builder().name("작가이름").build();
        Author author = authorRepository.save(authorMock);
//        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).build();
//        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
//        bookAndAuthors.add(bookAndAuthor);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
//                .bookAndAuthors(bookAndAuthors) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();

        // when
        Book result = bookRepository.save(book);

        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).book(result).build();
        bookAndAuthorRepository.save(bookAndAuthor);
        //bookAndAuthor 생성 및 save -> book, author에도 해당 bookAndAuthor 추가됨
        em.flush();
        em.clear();
        result = bookRepository.getById(result.getId());
        author = authorRepository.getById(author.getId());
//        Author result2 = authorRepository.getById(author.getId());

        //then
        assertThat(result.getBookReviewInfo().getId()).isNotNull(); // bookReviewInfo가 정상적으로 db에 저장
        assertThat(result.getId()).isNotNull(); // book이 정상적으로 db에 저장
        assertThat(result.getCategory()).isEqualTo("카테고리");
        assertThat(result.getName()).isEqualTo("이름");
        assertThat(result.getPublisher().getId()).isNotNull(); // publisher가 정상적으로 DB에 저장
        assertThat(result.getPublisher().getName()).isEqualTo("출판사");
        assertThat(result.getBookAndAuthors().get(0).getId()).isNotNull(); //bookAndAuthor가 정상적으로 DB에 저장
        assertThat(result.getBookAndAuthors().get(0).getAuthor().getName()).isEqualTo("작가이름");
        Long authorId = result.getBookAndAuthors().get(0).getAuthor().getId();
        assertThat(authorRepository.getById(authorId)).isNotNull(); // author가 정상적으로 DB에 저장
        assertThat(author.getBookAndAuthors().get(0).getId()).isEqualTo(result.getBookAndAuthors().get(0).getId());
        // author와 book이 갖고있는 bookAndAuthor가 동일함
        assertThat(result.getBookAndAuthors().get(0).getBook().getName()).isEqualTo("이름");
        assertThat(result.getStatus().getDescription()).isEqualTo("구매 가능");
        assertThat(author.getBookAndAuthors().size()).isNotEqualTo(0);

    }

    @Test
    @DisplayName("Book 수정 Success")
    public void BookRepositoryUpdateSuccessTest() {

        //given
        Author author = Author.builder().name("작가이름").build();
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        Review review = Review.builder().content("리뷰내용").score(4.5f).build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .reviews(reviews)
                .build();

        // when
        Book saved = bookRepository.save(book);
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).book(saved).build();
        BookAndAuthor savedBookAndAuthor = bookAndAuthorRepository.save(bookAndAuthor);
        saved.getReviews().get(0).setContent("리뷰 변경");
        saved.updateBasicInfo("다른 이름", saved.getCategory());

        em.flush();

        Book modified = bookRepository.save(saved);
        bookAndAuthor.updateBook(modified);
        bookAndAuthorRepository.save(bookAndAuthor);

        em.flush();
        em.clear();

        Book bookResult = bookRepository.getById(saved.getId());
        Author authorResult = authorRepository.getById(savedBookAndAuthor.getAuthor().getId());

        // then
        assertThat(modified.getId()).isEqualTo(saved.getId()); // 동일한 id 가짐
        assertThat(modified.getName()).isEqualTo("다른 이름"); // 수정된 내용 잘 반영됨
        assertThat(modified.getReviews().get(0).getContent()).isEqualTo("리뷰 변경");
        assertThat(reviewRepository.getById(saved.getReviews().get(0).getId()).getContent()).isEqualTo("리뷰 변경"); // DB에도 잘 반영됨
        assertThat(bookAndAuthorRepository.getById(savedBookAndAuthor.getId()).getBook().getName()).isEqualTo(modified.getName()); // bookAndAuthor에도 잘 반영됨
        assertThat(authorResult.getBookAndAuthors().get(0).getBook().getName()).isEqualTo(modified.getName()); //author의 bookAndAuthor 에도 잘 반영됨
    }

    @Test
    @DisplayName("Book 삭제 Success")
    public void BookRepositoryRemoveSuccessTest(){

        //given
        Author author = Author.builder().name("작가이름").build();
        Publisher publisher = Publisher.builder().name("출판사").build();
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        Review review = Review.builder().content("리뷰내용").score(4.5f).build();
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = Book.builder()
                .name("이름")
                .category("카테고리")
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .reviews(reviews) //cascade로 인해 자동으로 repository를 통해 save 될 것
                .build();

        // when
        Book saved = bookRepository.save(book);
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().book(saved).author(author).build();
        bookAndAuthorRepository.save(bookAndAuthor);
        //bookAndAuthor를 저장하면 book, author에도 해당내용 반영됨
        System.out.println(saved);

        em.flush();
        em.clear();

        Book finalBook = bookRepository.findById(saved.getId()).get();
        System.out.println(finalBook);

        bookRepository.deleteById(saved.getId());

//        em.flush();
//        em.clear();

        //then
        assertThat(bookRepository.findById(finalBook.getId()).isEmpty()).isTrue(); // 해당 book 삭제됨
        assertThat(bookReviewInfoRepository.findById(finalBook.getBookReviewInfo().getId()).isPresent()).isEqualTo(false); // 연관 bookReviewInfo 삭제됨
        assertThat(bookAndAuthorRepository.findById(finalBook.getBookAndAuthors().get(0).getId()).isPresent()).isEqualTo(true); // 연관 bookAndAuthor 삭제됨
        assertThat(publisherRepository.findById(finalBook.getPublisher().getId()).isPresent()).isEqualTo(true); // 연관 publisher 삭제되지 않음
        assertThat(publisherRepository.findById(finalBook.getPublisher().getId()).get().getBooks().size()).isNotEqualTo(0); // 연관 publisher에서 해당 book 제외되지 않음 !!!!!!!!!!!!!!
        assertThat(reviewRepository.findById(finalBook.getReviews().get(0).getId()).isPresent()).isEqualTo(false); // 연관 review 삭제됨
        assertThat(authorRepository.findById(finalBook.getBookAndAuthors().get(0).getAuthor().getId())).isNotNull(); // 연관 author 삭제되지 않음
        assertThat(authorRepository.findById(finalBook.getBookAndAuthors().get(0).getAuthor().getId()).get().getBookAndAuthors().size()).isEqualTo(0); // 연관 author에서 해당 bookAndAuthor 제외됨

    }
}
