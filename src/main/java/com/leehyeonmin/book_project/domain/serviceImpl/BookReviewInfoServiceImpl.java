package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.BookReviewInfo;
import com.leehyeonmin.book_project.domain.dto.BookReviewInfoDto;
import com.leehyeonmin.book_project.domain.service.BookReviewInfoService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.BookReviewInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class BookReviewInfoServiceImpl implements BookReviewInfoService {

    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Override
    public List<BookReviewInfoDto> findAllBookReviewInfos() {
        return bookReviewInfoRepository.findAll().stream().map(item -> ToDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public BookReviewInfoDto findBookReviewInfo(Long id) {
        BookReviewInfo entity = bookReviewInfoRepository.findById(id).orElse(null);
        return ToDto.from(entity);
    }

    @Override
    public BookReviewInfoDto addBookReviewInfo(BookReviewInfoDto dto) {
        BookReviewInfo entity = ToEntity.from(dto);
        BookReviewInfo saved = bookReviewInfoRepository.save(entity);
        return ToDto.from(saved);
    }

    @Override
    public BookReviewInfoDto modifyBookReviewInfo(BookReviewInfoDto dto) {
        BookReviewInfo entity = ToEntity.from(dto);
        BookReviewInfo saved = bookReviewInfoRepository.save(entity);
        return ToDto.from(saved);
    }

    @Override
    public Boolean removeBookReviewInfo(Long id) {
        if(bookReviewInfoRepository.findById(id).isPresent()) {
            bookReviewInfoRepository.deleteById(id);
            return true;
        } else return false;
    }
}