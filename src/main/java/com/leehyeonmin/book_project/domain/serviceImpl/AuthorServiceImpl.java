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

    final private RepoUtils repoUtils;

    @Override
    public AuthorDto.GetListResponse getAllAuthors() {
        return new AuthorDto.GetListResponse(authorRepository.findAll());
    }

    @Override
    public AuthorDto.GetResponse getAuthor(Long id) throws BusinessException{
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);
        return new AuthorDto.GetResponse(author);
    }

    @Override
    public AuthorDto.GetResponse addAuthor(AuthorDto dto){
        Author saved = authorRepository.save(toEntity.from(dto));
        return new AuthorDto.GetResponse(saved);
    }

    @Override
    public void removeAuthor(Long id) throws BusinessException {
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);

        // when - ????????? entity?????? ???????????? ????????? ????????? ??? ????????? ????????????.
        if(author.getBookAndAuthors().size() != 0)
            bookAndAuthorRepository.deleteAll(author.getBookAndAuthors());
        authorRepository.delete(author);
    }

    @Override
    public AuthorDto.GetResponse modifyBasicInfo(Long id, String name, String country) throws BusinessException{

        //author ??????
        Author author = repoUtils.getOneElseThrowException(authorRepository, id);

        // author ?????? ??? DB ??????
        author.updateBasicInfo(name, country); // ?????? ?????????????????? ?????????
        Author saved = authorRepository.save(author);

        // ??????
        return new AuthorDto.GetResponse(saved);
    }

    @Override
    public void addBookToAuthor(Long authorId, Long bookId) throws BusinessException{
        if(bookAndAuthorRepository.findByAuthorIdAndBookId(authorId, bookId).isPresent()) {
            throw new DuplicateEntityException("Duplicate BookAndAuthor Pair for id of author, book each : " + authorId + bookId);
        }
        //book, author ??????
        Author author = repoUtils.getOneElseThrowException(authorRepository, authorId);
        Book book = repoUtils.getOneElseThrowException(bookRepository, bookId);

        //bookAndAuthor ?????? ??? ?????? -> book, author??? ???????????? ?????????
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
