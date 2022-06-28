package com.leehyeonmin.book_project.domain.utils;

import com.leehyeonmin.book_project.domain.BaseEntity;
import com.leehyeonmin.book_project.domain.dto.BaseDto;
import com.leehyeonmin.book_project.domain.exception.BusinessException.DuplicateEntityException.DuplicateEntityException;
import com.leehyeonmin.book_project.domain.exception.BusinessException.EntityNotFoundException.EntityNotFoundException;
import com.leehyeonmin.book_project.domain.exception.ErrorCode;
import com.leehyeonmin.book_project.domain.exception.BusinessException.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class RepoUtils {

//    final private ToEntity toEntity;

//    final private ToDto toDto;

    public <R extends JpaRepository<E, Long>, E extends BaseEntity> E getOneElseThrowException(R repository, Long id) throws EntityNotFoundException{
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("no entity found by" + repository.getClass().getName() + " from id " + id));
    }

    public <R extends JpaRepository<E, Long>, E extends BaseEntity> void deleteOneElseThrowException(R repository, Long id) throws EntityNotFoundException{
        if(repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("no entity found by" + repository.getClass().getName() + " for id : " + id);
        }
    }

    public <R extends JpaRepository<E, Long>, E extends BaseEntity> void validateDuplicate(R repository, Long id) throws DuplicateEntityException{
        if(!repository.existsById(id)){
            throw new EntityNotFoundException("duplicate entity found by" + repository.getClass().getName() + " for id : " + id);
        }
    }

    public <R extends JpaRepository<E, Long>, E extends BaseEntity> void validateExist(R repository, Long id) throws DuplicateEntityException{
        if(repository.existsById(id)){
            throw new EntityNotFoundException("not existing entity searched by" + repository.getClass().getName() + " for id : " + id);
        }
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
