package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.PublisherDto;

import java.util.List;

public interface PublisherService {
    public List<PublisherDto> findAllPublishers();

    public PublisherDto findPublisher(Long id);

    public PublisherDto addPublisher(PublisherDto dto);

    public PublisherDto modifyPublisher(PublisherDto dto);

    public Boolean removePublisher(Long id);
}