package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherServiceImpl implements PublisherService {
    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public List<PublisherDto> findAllPublishers() {
        return publisherRepository.findAll().stream().map(item -> ToDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public PublisherDto findPublisher(Long id) {
        Publisher entity = publisherRepository.findById(id).orElse(null);
        return ToDto.from(entity);
    }

    @Override
    public PublisherDto addPublisher(PublisherDto dto) {
        Publisher entity = ToEntity.from(dto);
        Publisher saved = publisherRepository.save(entity);
        return ToDto.from(saved);
    }

    @Override
    public PublisherDto modifyPublisher(PublisherDto dto) {
        if(publisherRepository.findById(dto.getId()).isPresent()){
            return addPublisher(dto);
        } else {
            return null;
        }
    }

    @Override
    public Boolean removePublisher(Long id) {
        if(publisherRepository.findById(id).isPresent()){
            publisherRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
