package com.leehyeonmin.book_project.domain.controller;

import com.google.gson.Gson;
import com.leehyeonmin.book_project.domain.Enum.BookStatus;
import com.leehyeonmin.book_project.domain.Enum.Category;
import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookRestControllerTest {

    @InjectMocks
    private BookRestController bookRestController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() { mockMvc = MockMvcBuilders.standaloneSetup(bookRestController).build(); }

    @Test
    @DisplayName("book 추가 실패")
    public void addBookFail() throws Exception{
        // given
        BookDto request = bookDtoIn();
        BookDto response = bookDtoOut();
        lenient().when(bookService.addBook(any(BookDto.class))).thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(null))
        );

        // then
        MvcResult result = resultActions.andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    @DisplayName("getBooksForMainPage")
    public void getBooksForMainPage() throws Exception{
        // given
        String start = "0";
        String categoryCode = Category.ART.getCode();
        List<BookDto.GetResponse> books = bookDtoList3().stream()
                .map( book -> new BookDto.GetResponse(book))
                .collect(Collectors.toList());
        lenient().when(bookService.getBooksByCategory(any(String.class), anyInt(), anyInt()))
            .thenReturn(BookDto.GetListResponse.builder().books(books).total(bookDtoList3().size()).build());


        // when
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/main")
                .contentType(MediaType.APPLICATION_JSON)
                .param("category_code", String.valueOf(categoryCode))
                .param("start", String.valueOf(5))
        );

        // then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()").value(3))
                .andExpect(jsonPath("$.total").value(3));
    }

    @Test
    @DisplayName("book 추가 성공")
    public void addBookSuccess() throws Exception{
        // given
        BookDto request = bookDtoIn();
        BookDto response = bookDtoOut();
        lenient().when(bookService.addBook(any(BookDto.class))).thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("name", response.getName()).exists())
                .andExpect(jsonPath("category", response.getCategoryCode()).exists())
                .andExpect(jsonPath("id", response.getId()).exists());
    }

    @Test
    @DisplayName("book 리스트 가져오기 성공")
    public void getAllBooksSuccess() throws Exception{
        // given
        lenient().doReturn(BooksResponse.builder()
                        .books(bookDtoList())
                        .total(bookDtoList().size())
                        .build())
                .when(bookService).getAllBooks();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        BooksResponse response = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), BooksResponse.class);
        assertThat(response.getBooks().size()).isEqualTo(5);
    }



    private BookDto bookDtoIn(){
        return BookDto.builder()
                .statusCode(BookStatus.AVAILABLE.getCode())
                .name("책 이름")
                .categoryName("철학")
                .publisherId(1L)
                .authors(List.of(AuthorDto.builder().id(1L).build()))
                .build();
    }

    private BookDto bookDtoOut(){
        return BookDto.builder()
                .statusCode(BookStatus.AVAILABLE.getCode())
                .name("책 이름")
                .categoryName("철학")
                .categoryCode("100")
                .publisherId(1L)
                .authors(authorDtoList())
                .bookReviewInfoId(1L)
                .id(1L)
                .build();
    }

    private List<BookDto> bookDtoList(){
        List<BookDto> lst = new ArrayList<>();
        for(int i = 0; i < 5; i++ ){
            lst.add(BookDto.builder().id(Long.valueOf(i)).build());
        }
        return lst;
    }

    private List<BookDto> bookDtoList3(){
        List<BookDto> lst = new ArrayList<>();
        for(int i = 0; i < 3; i++ ){
            lst.add(BookDto.builder().id(Long.valueOf(i)).build());
        }
        return lst;
    }

    private List<AuthorDto> authorDtoList(){
        List<AuthorDto> lst = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            lst.add(AuthorDto.builder().id(Long.valueOf(i)).build());
        }
        return lst;
    }

}
