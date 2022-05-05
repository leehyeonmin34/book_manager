package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.service.AuthorService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    final private AuthorRepository authorRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    @Override
    public List<AuthorDto> findAllAuthors() {
        return authorRepository.findAll().stream().map(item -> ToDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findAuthor(Long id) {
        Author author = authorRepository.getById(id);
        return ToDto.from(author);
    }

    @Override
    public AuthorDto addAuthor(AuthorDto dto) {
        Author author = ToEntity.from(dto);
        System.out.println(dto);
        System.out.println(author);
        Author saved = authorRepository.save(author);
        System.out.println(saved);
        return ToDto.from(saved);
    }

    @Override
    public AuthorDto modifyAuthor(AuthorDto dto) {
        //author 수정
        Author author = ToEntity.from(dto);
        Author saved = authorRepository.save(author);

        //연관 bookAndAuthor 수정
        bookAndAuthorRepository.getByAuthorId(dto.getId()).stream().map(item -> {
            item.setAuthor(saved);
            return bookAndAuthorRepository.save(item);
        });

        return ToDto.from(saved);
    }

    @Override
    public Boolean removeAuthor(Long id) {
        if(authorRepository.findById(id).isPresent()){
            authorRepository.deleteById(id);

            //연관 bookAndAuthor 삭제
            bookAndAuthorRepository.getByAuthorId(id)
                    .stream().forEach(item -> bookAndAuthorRepository.delete(item));
            return true;
        } else {
            return false;
        }
    }
}
