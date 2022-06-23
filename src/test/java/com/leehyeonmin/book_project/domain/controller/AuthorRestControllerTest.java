package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthorRestControllerTest {
    @InjectMocks
    AuthorRestController authorRestController;

    @Mock
    AuthorService authorService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() { mockMvc = MockMvcBuilders
            .standaloneSetup(authorRestController)
            .build();
    }



}
