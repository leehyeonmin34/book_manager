package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.AuthorDto;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublisherRestController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<PublisherDto.GetListResponse> getAllPublishers(){
        return ResponseEntity.ok().body(publisherService.getAllPublishers());
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<PublisherDto.GetResponse> getAuthor(@PathVariable String id){
        return ResponseEntity.ok().body(publisherService.getPublisher(Long.valueOf(id)));
    }

    @PutMapping("/publisher")
    public ResponseEntity<PublisherDto.GetResponse> addPublisher(@RequestBody PublisherDto.Request request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publisherService.addPublisher(request.toServiceDto()));
    }

    @PostMapping("/publisher")
    public ResponseEntity modifyPublisher(@RequestBody PublisherDto.Request request){
        publisherService.updateBasicInfo(request.getId(), request.getName());
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/publisher/{id}")
    public ResponseEntity deletePublisher(@PathVariable String id){
        publisherService.removePublisher(Long.valueOf(id));
        return ResponseEntity.ok().body(null);
    }



}
