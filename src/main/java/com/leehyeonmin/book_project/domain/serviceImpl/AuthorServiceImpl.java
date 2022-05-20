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
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);

        // when - 연관된 entity들을 지우거나 관계를 끊어낸 후 자신을 삭제한다.
        if(author.getBookAndAuthors().size() != 0)
            bookAndAuthorRepository.deleteAll(author.getBookAndAuthors());
        authorRepository.delete(author);
    }

    @Override
    public AuthorDto modifyBasicInfo(Long id, String name, String country) throws BusinessException{

        //author 로드
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);

        // author 수정 및 DB 적용
        author.updateBasicInfo(name, country); // 연관 엔티티에서도 적용됨
        Author saved = authorRepository.save(author);

        // 리턴
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
        BookAndAuthor bookAndAuthor = BookAndAuthor.builder().build();
        bookAndAuthor.updateAuthor(author);
        bookAndAuthor.updateBook(book);
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
