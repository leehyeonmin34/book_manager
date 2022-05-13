package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Author;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.BookAndAuthor;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.DuplicateEntityException.DuplicateEntityException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.exception.NoEntityException;
import com.leehyeonmin.book_project.domain.service.AuthorService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import com.leehyeonmin.book_project.repository.BookAndAuthorRepository;
import com.leehyeonmin.book_project.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    final private AuthorRepository authorRepository;

    final private BookAndAuthorRepository bookAndAuthorRepository;

    final private BookRepository bookRepository;

    final private ToEntity toEntity;

    final private ToDto toDto;

    final private RepoUtils repoUtils;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(
                item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthor(Long id) throws BusinessException{
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);
        return toDto.from(author);
    }

    @Override
    public AuthorDto addAuthor(AuthorDto dto){
        Author saved = authorRepository.save(toEntity.from(dto));
        return toDto.from(saved);
    }

    @Override
    public void removeAuthor(Long id) throws BusinessException {
        repoUtils.validateExist(authorRepository, id);

        // 연관 book (bookAndAuthor 삭제) - cascade에 remove 포함되어있지 않으므로 book, author엔 영향 없음
        List<BookAndAuthor> bookAndAuthors = bookAndAuthorRepository.findByAuthorId(id);
        List<Long> idList = bookAndAuthors.stream().map(item -> item.getId()).collect(Collectors.toList());
        bookAndAuthorRepository.deleteAllById(idList);

        // author 삭제 - 최종적으로 book은 삭제 안되고 author와 bookAndAuthor만 삭제됨
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto modifyBasicInfo(Long id, String name, String country) throws BusinessException{

        //author 수정
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);
        author.updateBasicInfo(name, country);
        Author saved = authorRepository.save(author);

        //연관 bookAndAuthor 수정 -> book, author에도 적용ㅆ
        saved.getBookAndAuthors().forEach(item -> {
            item.updateAuthor(saved);
            bookAndAuthorRepository.save(item);
        });

        return toDto.from(saved);
    }

    @Override
    public void addBookToAuthor(Long authorId, Long bookId) throws BusinessException{
        if(bookAndAuthorRepository.findByAuthorIdAndBookId(authorId, bookId).isPresent()) {
            throw new DuplicateEntityException("Duplicate BookAndAuthor Pair for id of author, book each : " + authorId + bookId);
        }
        //book, author 로드
        Author author = repoUtils.getOneElseThrowException(authorRepository, authorId);
        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);

        //bookAndAuthor 생성 후 저장 -> book, author에 해당내용 반영됨
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder()
                .author(author)
                .book(book)
                .build();

        bookAndAuthorRepository.save(bookAndAuthor);


    }

    @Override
    public void removeBookFromAuthor(Long authorId, Long bookId) throws BusinessException {
        Optional<BookAndAuthor> bookAndAuthor = bookAndAuthorRepository.findByAuthorIdAndBookId(authorId, bookId);
        if (bookAndAuthor.isPresent()) {
            bookAndAuthorRepository.delete(bookAndAuthor.get());
        } else {
            throw new EntityNotFoundException("no entity found by bookAndAuthorRepository for if of author, book each " + authorId + bookId);
        }
    }
}
