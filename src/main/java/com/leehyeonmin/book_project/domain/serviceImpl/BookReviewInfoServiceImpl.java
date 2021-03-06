package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.BookReviewInfo;
import com.leehyeonmin.book_project.domain.dto.BookReviewInfoDto;
import com.leehyeonmin.book_project.domain.service.BookReviewInfoService;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.BookReviewInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookReviewInfoServiceImpl {

    final private BookReviewInfoRepository bookReviewInfoRepository;

    final private ToEntity toEntity;


//    @Override
//    public List<BookReviewInfoDto> findAllBookReviewInfos() {
//        return bookReviewInfoRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
//    }
//
//    @Override
//    public BookReviewInfoDto findBookReviewInfo(Long id) {
//        BookReviewInfo entity = bookReviewInfoRepository.findById(id).orElse(null);
//        return toDto.from(entity);
//    }
//
//    @Override
//    public BookReviewInfoDto addBookReviewInfo(BookReviewInfoDto dto) {
//        BookReviewInfo entity = toEntity.from(dto);
//        BookReviewInfo saved = bookReviewInfoRepository.save(entity);
//        return toDto.from(saved);
//    }
//
//    @Override
//    public BookReviewInfoDto modifyBookReviewInfo(BookReviewInfoDto dto) {
//        BookReviewInfo entity = toEntity.from(dto);
//        BookReviewInfo saved = bookReviewInfoRepository.save(entity);
//        return toDto.from(saved);
//    }
//
//    @Override
//    public Boolean removeBookReviewInfo(Long id) {
//        if(bookReviewInfoRepository.findById(id).isPresent()) {
//            bookReviewInfoRepository.deleteById(id);
//            return true;
//        } else return false;
//    }
}