package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.Enum.BookStatus;
import com.leehyeonmin.book_project.domain.Enum.Category;
import com.leehyeonmin.book_project.domain.dto.*;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.InvalidValueException.InvalidValueException;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BookServiceImpl implements BookService {

    final private BookRepository bookRepository;

    final private PublisherRepository publisherRepository;

    final private AuthorRepository authorRepository;

    final private BookReviewInfoRepository bookReviewInfoRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    final private ReviewRepository reviewRepository;

    final private ToEntity toEntity;

    final private RepoUtils repoUtils;

    @PersistenceContext
    final private EntityManager em;

    @Override
    public BookDto.GetListResponse getAllBooks() {
        return new BookDto.GetListResponse(bookRepository.findAll());
    }

    @Override
    public BookDto.GetResponse getBook(Long id) throws BusinessException{
        Book entity = repoUtils.getOneElseThrowException(bookRepository, id);
        return new BookDto.GetResponse(entity);
    }

    @Override
    public Page<BookDto> getBooksByCategory(String categoryCode, Pageable pageable){
        if (categoryCode == Category.ALL.getCode())
            return bookRepository.findAll(pageable).map(BookDto::new);
        else
            return bookRepository.findByCategory(Category.ofCode(categoryCode), pageable).map(BookDto::new);
    }

//    @Override
//    public int getTotalByCategory(String categoryCode){
//        if (categoryCode == Category.ALL.getCode())
//            return bookRepository.count().intValue();
//        else
//            return bookRepository.countByCategory(categoryCode).intValue();
//    }

    @Override
    public BookDto addBook(BookDto dto) throws BusinessException { // author, publisher??? ????????? ??????
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, dto.getPublisherId());
        List<Author> authorList = authorRepository.findAllById(
                dto.getAuthors().stream().map( author -> author.getId())
                        .collect(Collectors.toList()));
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();

        // ???????????? ?????? ?????? ?????? ?????? ???
        Book book = Book.builder()
                .name(dto.getName())
                .bookReviewInfo(bookReviewInfo) //cascade??? ?????? ???????????? repository ?????? save ??? ???
                .build();
        bookRepository.save(book);

        // ???????????? ????????? ?????? ??????
        // publisher
        book.updatePublisher(publisher); // ?????? publisher?????? ?????????
        book.updateBasicInfo(book.getName(), Category.ofCode(dto.getCategoryCode()));

        // bookAndAuthor, author ????????????
        List<BookAndAuthor> bookAndAuthors = authorList.stream().map(author -> {
            BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
            bookAndAuthor.updateBook(book); // ?????? book?????? ?????????
            bookAndAuthor.updateAuthor(author); // ?????? author?????? ?????????
            return bookAndAuthor;
        }).collect(Collectors.toList());

        bookAndAuthorRepository.saveAll(bookAndAuthors);

        // ????????? ???????????? ?????? DB??? ??????
        Book result = bookRepository.save(book);

        // ??????
        return new BookDto(result);
    }

    @Override
    public BookDto modifyBasicInfo(Long id, String name, String categoryCode) throws BusinessException{
        //?????? ???????????? book??? ?????? ??? ??????
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);
        book.updateBasicInfo(name, Category.ofCode(categoryCode));
        Book result = bookRepository.save(book); // ??? bookAndAuthor??? ????????? book ?????? ??????
        return new BookDto(result);
    }

    @Override
    public void changeBookStatus(Long id, String statusCode) throws BusinessException{
        // ?????? book ??????
        Book book = repoUtils.getOneElseThrowException(bookRepository, id);

        // book??? status ????????????
        book.updateStatus(BookStatus.ofCode(statusCode));

        //?????? ??? ??????
        bookRepository.save(book);
    }


    @Override
    public BookDto changePublisherOfBook(Long bookId, Long publisherId) throws BusinessException{
        //?????? book, publisher ??????
        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, publisherId);

        // book??? publisher ????????????
        book.updatePublisher(publisher); // ?????? publisher?????? ?????????

        // ?????? ??? ??????
        Book result = bookRepository.save(book);
        return new BookDto(result);
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
