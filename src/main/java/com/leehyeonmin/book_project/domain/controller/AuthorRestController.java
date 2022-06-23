package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;
}
