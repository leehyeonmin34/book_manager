package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.BookReviewInfoRepository;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    BookAndAuthorRepository bookAndAuthorRepository;

    @Override
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream().map(item -> ToDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public BookDto findBook(Long id) {
        Book entity = bookRepository.findById(id).orElse(null);
        return ToDto.from(entity);
    }

    //퍼블리셔id 유무에 따른 처리 필요
    @Override
    public BookDto addBook(BookDto dto) {

        Book entity = ToEntity.from(dto);
        Book saved = bookRepository.save(entity);

        return ToDto.from(saved);
    }

    @Override
    public BookDto modifyBook(BookDto dto) {
        if(bookRepository.findById(dto.getId()).isPresent()){
            return addBook(dto);
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
