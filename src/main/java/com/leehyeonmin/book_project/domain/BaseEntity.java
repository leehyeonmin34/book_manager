package com.leehyeonmin.book_project.domain;

import com.leehyeonmin.book_project.domain.listener.Auditable;
import com.leehyeonmin.book_project.domain.listener.MyEntityListener;
import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EntityListeners(MyEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Auditable {

    @Column(updatable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
