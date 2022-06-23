package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublisherRestController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<PublisherDto.GetListResponse> getAllPublishers(){
        return ResponseEntity.ok().body(publisherService.getAllPublishers());
    }

}
