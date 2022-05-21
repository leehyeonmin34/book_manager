package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private static int SHOWING_MAIN = 5;

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable @NotNull Long id){
        return ResponseEntity.ok()
                .body(bookService.getBook(id));
    }

    @GetMapping("/books")
    public ResponseEntity<BooksResponse> getBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/books/main")
    public ResponseEntity<BooksResponse> getBooksForMainPage(
            @RequestParam(name = "page_num", defaultValue = "0", required = false) int pageNum,
            @RequestParam(name = "category_id", defaultValue = "0", required = false) Long categoryId
    ){
        int start = ( pageNum - 1 ) * SHOWING_MAIN + 1;
        BooksResponse response = bookService.getBooksByCategoryId(categoryId, start, start + SHOWING_MAIN);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("book/add")
    public ResponseEntity<BookDto> addBook(@RequestBody @Validated(ValidationGroups.normal.class) BookDto dto){
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(dto));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable @NotNull Long id){
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/basic_info")
    public ResponseEntity<BookDto> updateBasicInfo(@RequestParam(name="id") @NotNull Long id, @RequestParam(name="name") @NotNull String name, @RequestParam(name="category") @NotNull String category){
        bookService.modifyBasicInfo(id, name, category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_status")
    public ResponseEntity changeBookStatus(@RequestParam(name="id") @NotNull Long bookId, @RequestParam(name="status") int statusCode){
        bookService.changeBookStatus(bookId, statusCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_publisher")
    public ResponseEntity changePublisherOfBook(@RequestParam @NotNull Long bookId, @RequestParam @NotNull Long publisherId){
        bookService.changePublisherOfBook(bookId, publisherId);
        return ResponseEntity.ok().build();
    }





}
