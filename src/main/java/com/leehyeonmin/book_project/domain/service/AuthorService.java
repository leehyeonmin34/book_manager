package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.repository.AuthorRepository;

import java.util.List;

public interface AuthorService {



    public List<AuthorDto> findAllAuthors();

    public AuthorDto findAuthor(Long id);

    public AuthorDto addAuthor(AuthorDto dto);

    public AuthorDto modifyAuthor(AuthorDto dto);

    public Boolean removeAuthor(Long id);

}
