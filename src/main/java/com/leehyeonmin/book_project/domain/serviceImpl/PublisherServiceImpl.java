package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Publisher;
import com.leehyeonmin.book_project.domain.dto.PublisherDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.service.PublisherService;
import com.leehyeonmin.book_project.domain.utils.RepoUtils;
import com.leehyeonmin.book_project.domain.utils.ToEntity;
import com.leehyeonmin.book_project.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublisherServiceImpl implements PublisherService {
    final private PublisherRepository publisherRepository;

    final private ToEntity toEntity;
    final private RepoUtils repoUtils;

    @Override
    public PublisherDto.GetListResponse getAllPublishers() {
        return new PublisherDto.GetListResponse(publisherRepository.findAll());
    }

    @Override
    public PublisherDto.GetResponse getPublisher(Long id) {
        Publisher entity = repoUtils.getOneElseThrowException(publisherRepository, id);
        return new PublisherDto.GetResponse(entity);
    }

    @Override
    public PublisherDto.GetResponse addPublisher(PublisherDto dto) {
        Publisher entity = toEntity.from(dto);
        Publisher saved = publisherRepository.save(entity);
        return new PublisherDto.GetResponse(saved);
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
