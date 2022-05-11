package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.service.AuthorService;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl extends RuntimeException implements AuthorService {

    final private AuthorRepository authorRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    final private ToEntity toEntity;

    final private ToDto toDto;

    @Override
    public List<AuthorDto> findAllAuthors() {
        return authorRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findAuthor(Long id) {
        Author author = authorRepository.getById(id);
        return toDto.from(author);
    }

    @Override
    public AuthorDto addAuthor(AuthorDto dto){
        if(dto == null) throw new NoEntityException("authorDto가 null입니다.");
        Author author = toEntity.from(dto);
        Author saved = authorRepository.save(author);
        return toDto.from(saved);
    }

    @Override
    public AuthorDto modifyAuthor(AuthorDto dto) {

        if(dto == null) throw new NoEntityException("authorDto가 null입니다.");

        //author 수정
        if(!authorRepository.findById(dto.getId()).isPresent()){
            return null;
        }
        Author author = toEntity.from(dto);
        Author saved = authorRepository.save(author);

        //연관 bookAndAuthor 수정
        // TODO : cascade에 의해 자동으로 수정되나?
        List<BookAndAuthor> bookAndAuthorList = bookAndAuthorRepository.getByAuthorId(dto.getId());
        bookAndAuthorList.forEach(item -> {
            item.updateAuthor(saved);
            bookAndAuthorRepository.save(item);
        });

        return toDto.from(saved);
    }

    @Override
    public Boolean removeAuthor(Long id) {
        if(id == null) throw new NoEntityException("id가 null입니다.");

        if(authorRepository.findById(id).isPresent()){
            authorRepository.deleteById(id);
            // 연관 bookAndAuthor는 cascade에 의해 삭제

            return true;
        } else {
            return false;
        }
    }
}
