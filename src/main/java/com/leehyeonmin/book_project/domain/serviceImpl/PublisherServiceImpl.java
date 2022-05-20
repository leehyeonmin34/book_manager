package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
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
    final private RepoUtils repoUtils;

    @Override
    public List<PublisherDto> findAllPublishers() {
        return publisherRepository.findAll().stream().map(item -> toDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public PublisherDto findPublisher(Long id) {
        Publisher entity = repoUtils.getOneElseThrowException(publisherRepository, id);
        return toDto.from(entity);
    }

    @Override
    public PublisherDto addPublisher(PublisherDto dto) {
        Publisher entity = toEntity.from(dto);
        Publisher saved = publisherRepository.save(entity);
        return toDto.from(saved);
    }

    @Override
    public void updateBasicInfo(Long id, String name) {
        Publisher publisher = repoUtils.getOneElseThrowException(publisherRepository, id);
        publisher.updateBasicInfo(name);
    }

    @Override
    public void removePublisher(Long id) {
        repoUtils.deleteOneElseThrowException(publisherRepository, id);
    }
}
