package com.leehyeonmin.book_project.domain.listener;

import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.UserHistory;
import com.leehyeonmin.book_project.repository.UserHistoryRepository;
import com.leehyeonmin.book_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
public class UserTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Test
    @Transactional
    public void ListenerTest(){
        User user = new User();

        userRepository.save(user);
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);

        user.setEmail("leehyeonin34@gmail.com");
        userRepository.save(user);
        List<UserHistory> userHistories = userHistoryRepository.findAll();
        System.out.println(">>> count" + userHistoryRepository.count());
        userHistories.forEach(System.out::println);
    }
}
