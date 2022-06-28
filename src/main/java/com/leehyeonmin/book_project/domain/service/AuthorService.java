package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {



    public AuthorDto.GetListResponse getAllAuthors();

    public AuthorDto.GetResponse getAuthor(Long id);

    public AuthorDto.GetResponse addAuthor(AuthorDto dto);

    public AuthorDto.GetResponse modifyBasicInfo(Long id, String name, String country);

    public void addBookToAuthor(Long authorId, Long bookId);

    public void removeBookFromAuthor(Long authorId, Long bookId);

    public void removeAuthor(Long id);

}
