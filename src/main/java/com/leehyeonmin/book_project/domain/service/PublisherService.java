package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.PublisherDto;

import java.util.List;

public interface PublisherService {
    public PublisherDto.GetListResponse getAllPublishers();

    public PublisherDto.GetResponse getPublisher(Long id);

    public PublisherDto.GetResponse addPublisher(PublisherDto dto);

    public void updateBasicInfo(Long id, String name);

    public void removePublisher(Long id);
}
