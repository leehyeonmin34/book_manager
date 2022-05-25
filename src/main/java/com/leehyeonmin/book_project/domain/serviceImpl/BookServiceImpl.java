package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.Enum.BookStatus;
import com.leehyeonmin.book_project.domain.Enum.Category;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    public BookDto.GetListResponse getAllBooks() {
        List<BookDto> bookDtos = bookRepository.findAll().stream()
                .map(item -> toDto.from(item))
                .collect(Collectors.toList());

        BookDto.GetListResponse response = new BookDto.GetListResponse(bookDtos);
        return response;
    }

    @Override
    public BookDto.GetResponse getBook(Long id) throws BusinessException{
        Book entity = repoUtils.getOneElseThrowException(bookRepository, id);
        return new BookDto.GetResponse(toDto.from(entity));
    }

    @Override
    public Page<BookDto> getBooksByCategory(String categoryCode, Pageable pageable){
        return bookRepository.findByCategory(categoryCode, pageable).map(BookDto::new);
    }

//    @Override
//    public int getTotalByCategory(String categoryCode){
//        if (categoryCode == Category.ALL.getCode())
//            return bookRepository.count().intValue();
//        else
//            return bookRepository.countByCategory(categoryCode).intValue();
//    }

    @Override
    public BookDto addBook(BookDto dto) throws BusinessException { // author, publisher의 유무에 따라
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, dto.getPublisherId());
        List<Author> authorList = authorRepository.findAllById(
                dto.getAuthors().stream().map( author -> author.getId())
                        .collect(Collectors.toList()));
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();

        // 연관관계 필요 없는 정보 바로 셋
        Book book = Book.builder()
                .name(dto.getName())
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();
        bookRepository.save(book);

        // 연관관계 필요한 정보 설정
        // publisher
        book.updatePublisher(publisher); // 해당 publisher에도 적용됨
        book.updateBasicInfo(book.getName(), Category.ofCode(dto.getCategoryCode()));

        // bookAndAuthor, author 업데이트
        List<BookAndAuthor> bookAndAuthors = authorList.stream().map(author -> {
            BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
            bookAndAuthor.updateBook(book); // 해당 book에도 적용됨
            bookAndAuthor.updateAuthor(author); // 해당 author에도 적용됨
            return bookAndAuthor;
        }).collect(Collectors.toList());

        bookAndAuthorRepository.saveAll(bookAndAuthors);

        // 추가된 정보까지 모두 DB에 반영
        Book result = bookRepository.save(book);

        // 리턴
        return toDto.from(result);
    }

    @Override
    public BookDto modifyBasicInfo(Long id, String name, String categoryCode) throws BusinessException{
        //해당 아이디의 book을 로드 후 수정
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);
        book.updateBasicInfo(name, Category.ofCode(categoryCode));
        Book result = bookRepository.save(book); // 새 bookAndAuthor가 적용된 book 다시 로드
        return toDto.from(result);
    }

    @Override
    public void changeBookStatus(Long id, String statusCode) throws BusinessException{
        // 해당 book 로드
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);

        // book에 status 업데이트
        book.updateStatus(BookStatus.ofCode(statusCode));

        //저장 후 리턴
        bookRepository.save(book);
    }


    @Override
    public BookDto changePublisherOfBook(Long bookId, Long publisherId) throws BusinessException{
        //해당 book, publisher 로드
        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, publisherId);

        // book에 publisher 업데이트
        book.updatePublisher(publisher); // 해당 publisher에도 적용됨

        // 저장 후 리턴
        Book result = bookRepository.save(book);
        return toDto.from(result);
    }

    @Override
    public void removeBook(Long id) throws BusinessException{
        repoUtils.deleteOneElseThrowException(bookRepository, id);
    }

//    @Override
//    public List<ReviewDto.GetResponse> getAllReviews(Long bookId) throws BusinessException{
//        if(bookRepository.existsById(bookId)){
//            return reviewRepository
//                    .getByBookId(bookId).stream().map(
//                            review -> { return toDto.from(review); }
//                    ).collect(Collectors.toList());
//        } else throw new EntityNotFoundException("invalid bookId " + bookId.toString());
//    }

}
