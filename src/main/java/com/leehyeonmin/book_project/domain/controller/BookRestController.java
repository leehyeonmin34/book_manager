package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.BookDto;
import com.leehyeonmin.book_project.domain.response.BooksResponse;
import com.leehyeonmin.book_project.domain.service.BookService;
import com.leehyeonmin.book_project.domain.validations.ValidationGroups;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<BookDto.GetResponse> getBook(@PathVariable @NotNull Long id){
        return ResponseEntity.ok()
                .body(bookService.getBook(id));
    }

    @GetMapping("/books")
    public ResponseEntity<BookDto.GetListResponse> getBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/books/main")
    public ResponseEntity<Page<BookDto>> getBooksForMainPage(
            @RequestParam(name = "category_code", defaultValue = "0", required = false) String categoryCode,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ){
        return ResponseEntity.ok().body(bookService.getBooksByCategory(categoryCode, pageable));
    }

    @PostMapping("book/add")
    public ResponseEntity<BookDto> addBook(@RequestBody @Validated(ValidationGroups.normal.class) BookDto.AddRequest request){
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(request.toServiceDto()));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable @NotNull Long id){
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/basic_info")
    public ResponseEntity<BookDto> updateBasicInfo(@RequestParam(name="id") @NotNull Long id,
                                                   @RequestParam(name="name") @NotNull String name,
                                                   @RequestParam(name="category") @NotNull String categoryCode){
        bookService.modifyBasicInfo(id, name, categoryCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_status")
    public ResponseEntity changeBookStatus(@RequestParam(name="id") @NotNull Long bookId,
                                           @RequestParam(name="status") String statusCode){
        bookService.changeBookStatus(bookId, statusCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/change_publisher")
    public ResponseEntity changePublisherOfBook(@RequestParam @NotNull Long bookId,
                                                @RequestParam @NotNull Long publisherId){
        bookService.changePublisherOfBook(bookId, publisherId);
        return ResponseEntity.ok().build();
    }





}
