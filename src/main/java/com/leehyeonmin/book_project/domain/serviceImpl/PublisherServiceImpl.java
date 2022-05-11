package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import com.leehyeonmin.book_project.domain.utils.ToDto;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    final private PublisherRepository publisherRepository;

    final private ToEntity toEntity;
    final private ToDto toDto;
    @Override
    public List<PublisherDto> findAllPublishers() {
        return publisherRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public PublisherDto findPublisher(Long id) {
        Publisher entity = publisherRepository.findById(id).orElse(null);
        return toDto.from(entity);
    }

    @Override
    public PublisherDto addPublisher(PublisherDto dto) {
        Publisher entity = toEntity.from(dto);
        Publisher saved = publisherRepository.save(entity);
        return toDto.from(saved);
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
