package com.leehyeonmin.book_project.domain.listener;

import com.leehyeonmin.book_project.BeanUtils;
import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.UserHistory;
import com.leehyeonmin.book_project.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

public class UserEntityListener {
    @PostPersist
    @PostUpdate
    public void prePersistAndPrePersist(Object o){

        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) o;


        UserHistory.UserHistoryBuilder builder = UserHistory.builder();
        UserHistory history = builder.user(user)
                .email(user.getEmail())
                .name(user.getName())
                .build();

        userHistoryRepository.save(history);
    }
}
