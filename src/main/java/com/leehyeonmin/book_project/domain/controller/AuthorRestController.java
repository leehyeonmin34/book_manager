package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;


    @GetMapping("/authors")
    public ResponseEntity<AuthorDto.GetListResponse> getAllAuthors(){
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto.GetResponse> getAuthor(@PathVariable String id){
        return ResponseEntity.ok().body(authorService.getAuthor(Long.valueOf(id)));
    }

    @PutMapping("/author")
    public ResponseEntity<AuthorDto.GetResponse> addAuthor(@RequestBody AuthorDto.Request request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.addAuthor(request.toServiceDto()));
    }

    @PostMapping("/author")
    public ResponseEntity<AuthorDto.GetResponse> modifyPublisher(@RequestBody AuthorDto.Request request){
        authorService.modifyBasicInfo(request.getId(), request.getName(), request.getCountry());
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity deletePublisher(@PathVariable String id){
        authorService.removeAuthor(Long.valueOf(id));
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/author/addBook")
    public ResponseEntity addBookToAuthor(
            @RequestParam(name = "authorId", required = true) Long authorId,
            @RequestParam(name = "bookId", required = true) Long bookId){
        authorService.addBookToAuthor(authorId, bookId);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/author/removeBook")
    public ResponseEntity removeBookFromAuthor(
            @RequestParam(name = "authorId", required = true) Long authorId,
            @RequestParam(name = "bookId", required = true) Long bookId){
        authorService.removeBookFromAuthor(authorId, bookId);
        return ResponseEntity.ok().body(null);
    }



}
