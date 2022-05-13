package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.repository.AuthorRepository;

import java.util.List;

public interface AuthorService {



    public List<AuthorDto> getAllAuthors();

    public AuthorDto getAuthor(Long id);

    public AuthorDto addAuthor(AuthorDto dto);

    public AuthorDto modifyBasicInfo(Long id, String name, String country);

    public void addBookToAuthor(Long authorId, Long bookId);

    public void removeBookFromAuthor(Long authorId, Long bookId);

    public void removeAuthor(Long id);

}
