package com.leehyeonmin.book_project.domain;

import com.leehyeonmin.book_project.domain.listener.Auditable;
import com.leehyeonmin.book_project.domain.listener.MyEntityListener;
import com.leehyeonmin.book_project.domain.utils.EntityInterface;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EntityListeners(MyEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity implements Auditable, EntityInterface {

    @Column(columnDefinition = "datetime(6) default now(6) comment '실행시간'", updatable = false, nullable = false)
    LocalDateTime createdAt;

    @Column(columnDefinition = "datetime(6) default now(6) comment '수정시간'", nullable = false)
    LocalDateTime updatedAt;

}
