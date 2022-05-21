package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable @NotNull Long id){
        return ResponseEntity.ok()
                .body(bookService.getBook(id));
    }

    @GetMapping("/books")
    public ResponseEntity<BooksResponse> getBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @PostMapping("book/add")
    public ResponseEntity<BookDto> addBook(@RequestBody @NotNull BookDto dto){
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(dto));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id){
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/basic_info")
    public ResponseEntity<BookDto> updateBasicInfo(@RequestParam(name="id") @NotNull Long id, @RequestParam(name="name") String name, @RequestParam(name="category") String category){
        bookService.modifyBasicInfo(id, name, category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_status")
    public ResponseEntity changeBookStatus(@RequestParam(name="id") @NotNull Long bookId, @RequestParam(name="status") int statusCode){
        bookService.changeBookStatus(bookId, statusCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_publisher")
    public ResponseEntity changePublisherOfBook(@RequestParam @NotNull Long bookId, @RequestParam Long publisherId){
        bookService.changePublisherOfBook(bookId, publisherId);
        return ResponseEntity.ok().build();
    }





}
