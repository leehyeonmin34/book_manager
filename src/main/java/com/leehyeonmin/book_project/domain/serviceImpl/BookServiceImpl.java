package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.request.AddBookRequest;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {


    final private BookRepository bookRepository;

    final private PublisherRepository publisherRepository;

    final private AuthorRepository authorRepository;

    final private BookReviewInfoRepository bookReviewInfoRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    final private ReviewRepository reviewRepository;

    final private ToEntity toEntity;

    final private ToDto toDto;

    final private RepoUtils repoUtils;

    @PersistenceContext
    final private EntityManager em;

    @Override
    public BooksResponse getAllBooks() {
        List<BookDto> bookDtos = bookRepository.findAll().stream()
                .map(item -> toDto.from(item))
                .collect(Collectors.toList());

        BooksResponse response = BooksResponse.builder()
                .books(bookDtos)
                .total(bookDtos.size())
                .build();
        return response;
    }

    @Override
    public BookDto getBook(Long id) throws BusinessException{
        Book entity = repoUtils.getOneElseThrowException(bookRepository, id);
        return toDto.from(entity);
    }

    @Override
    public BookDto addBook(BookDto dto) throws BusinessException { // author, publisher의 유무에 따라
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, dto.getPublisherId());
        Author author = repoUtils.getOneElseThrowException(authorRepository, dto.getAuthorId());
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();

        Book book = Book.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();
        Book saved = bookRepository.save(book);

        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .author(author)
                .book(saved)
                .build();
        bookAndAuthorRepository.save(bookAndAuthor);


        em.flush(); // bookAndAuthor를 DB에 저장, book, author에도 연관관계 적용됨
        em.clear(); // entity cache 제거
        Book result = bookRepository.getById(saved.getId()); // author가 추가된 book을 다시 로드

        return toDto.from(result);

    }



    @Override
    public BookDto modifyBasicInfo(Long id, String name, String category) throws BusinessException{
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);
        book.updateBasicInfo(name, category);
        //해당 아이디의 book을 로드 후 수정

        book.getBookAndAuthors()
                .forEach(item -> {
                    item.updateBook(book);
                    bookAndAuthorRepository.save(item);
                    // 연관된 bookAndAuthor들의 book 수정
                });

        em.flush(); // bookAndAuthor가 DB에 저장되고, book과 author가 각각 bookAndAuthor를 가짐
        em.clear(); // 영속성 캐시 제거
        Book result = bookRepository.getById(book.getId()); // 새 bookAndAuthor가 적용된 book 다시 로드
        return toDto.from(result);
    }

    @Override
    public void changeBookStatus(Long id, int statusCode) throws BusinessException{
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);
        book.updateStatus(statusCode);
        bookRepository.save(book);
    }


//    @Override
//    public BookDto changeAuthorOfBook(Long bookId, Long authorId) throws BusinessException{
//        //book, author 로드
//        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);
//        Author author = repoUtils.getOneElseThrowException(authorRepository, authorId);
//
//        // book 내의 bookAndAuthor 수정요청 -> book과 author 모두 수정됨 (cascade)
//        book.getBookAndAuthors()
//                .forEach(item -> {
//                    item.updateAuthor(author);
//                    bookAndAuthorRepository.save(item);
//                });
////        EntityManager em = emf.createEntityManager();
//        em.flush(); // DB 적용 완료
//        em.clear(); // 영속성 캐시 제거
//        Book result = bookRepository.getById(book.getId()); // 적용된 book 불러오기
//        return toDto.from(result);
//    }

    @Override
    public BookDto changePublisherOfBook(Long bookId, Long publisherId) throws BusinessException{
        //해당 book, publisher 로드
        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, publisherId);

        // book에 publisher 업데이트
        book.updatePublisher(publisher);
        em.flush(); // DB에 적용, book, publisher 모두에 적용됨
        em.clear(); // 영속성 캐시 제거
        Book result = bookRepository.getById(book.getId()); // 적용된 book 불러오기
        return toDto.from(result);
    }

    @Override
    public void removeBook(Long id) throws BusinessException{
        repoUtils.deleteOneElseThrowException(bookRepository, id);
    }

    @Override
    public List<ReviewDto> getAllReviews(Long bookId) throws BusinessException{
        if(bookRepository.existsById(bookId)){
            return reviewRepository
                    .getByBookId(bookId).stream().map(
                            review -> { return toDto.from(review); }
                    ).collect(Collectors.toList());
        } else throw new EntityNotFoundException("invalid bookId " + bookId.toString());
    }

}
