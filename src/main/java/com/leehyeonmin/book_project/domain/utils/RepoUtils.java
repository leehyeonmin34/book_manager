package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.dto.BaseDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.exception.ErrorCode;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
public class RepoUtils {

    final private ToEntity toEntity;

    final private ToDto toDto;

    public <R extends JpaRepository<E, Long>, E extends BaseEntity> E getOneElseThrowException(R repository, Long id) throws EntityNotFoundException{
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no entity for repoUtils of" + repository.getClass().toString() + " from id " + id));
    }


//    public <R extends JpaRepository<E, Long>, E extends BaseEntity, D extends BaseDto> E getOrCreate(R repository, D dto){
//        return repository.findById(dto.getId())
//                .orElseGet(() -> repository.save(toEntity.from(dto)));
//    }

//    public <E extends BaseEntity, R extends JpaRepository<E, Long>, D extends BaseDto> E getOrCreate(R repository, Long id){
//        E e = new E();
//        return repository.findById(id)
//                .orElseGet(() -> repository.save(new E());
//        repository.findById().
//    }

}
