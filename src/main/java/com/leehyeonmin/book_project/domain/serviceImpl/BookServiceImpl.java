package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.*;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.request.AddBookRequest;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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

    final private ToEntity toEntity;

    final private ToDto toDto;

    @Override
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public BookDto findBook(Long id) {
        Book entity = bookRepository.findById(id).orElse(null);
        return toDto.from(entity);
    }

    //퍼블리셔id 유무에 따른 처리 필요
    @Override
    public BookDto addBook(AddBookRequest request) {
        // controller에서, addBook 실행 전 addAuthor, addPublisher 먼저 실행필요
        Author author;
        Publisher publisher;
        BookDto bookDto = request.getBookDto();
        if (bookDto.getAuthorId() == null) {
            author = toEntity.from(request.getAuthorDto());
        } else {author = authorRepository.getById(bookDto.getAuthorId());}
        if (bookDto.getPublisherId() == null) {
            publisher = toEntity.from(request.getPublisherDto());
        } else {
            publisher = publisherRepository.getById(bookDto.getPublisherId());
        }
        BookReviewInfo bookReviewInfo = BookReviewInfo.builder().build();
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().author(author).build();
        List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
        bookAndAuthors.add(bookAndAuthor); //book의 bookAndAuthor

        Book book = Book.builder()
                .name(bookDto.getName())
                .category(bookDto.getCategory())
                .bookReviewInfo(bookReviewInfo) //cascade로 인해 자동으로 repository 통해 save 될 것
                .bookAndAuthors(bookAndAuthors) //cascade로 인해 자동으로 repository 통해 save 될 것
                .publisher(publisher) //cascade로 인해 자동으로 repository 통해 save 될 것
                .build();
        Book saved = bookRepository.save(book);

        author.getBookAndAuthors().add(bookAndAuthor); //author의 bookAndAuthor
        authorRepository.save(author);

        return toDto.from(saved);
    }

    @Override
    public BookDto modifyBook(BookDto dto) {
        if(bookRepository.findById(dto.getId()).isPresent()){
            return null;
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeBook(Long id) {
        if(bookRepository.findById(id).isPresent()){
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
