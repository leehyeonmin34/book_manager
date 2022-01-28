package com.leehyeonmin.book_project.domain;

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
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private EntityManager em;

    @Test
    void crud(){
        userRepository.save(new User());

        List<User> users = userRepository.findAll();
//		List<User> users = userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L));
        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    void crud2(){
        User user = new User();
        user.setEmail("hi~");
        user.setName("name");
        userRepository.save(user);
        user.setName("Hey it's me");
        em.detach(user);
        user.setName("after Detaching");
        User user2 = em.merge(user);
        user2.setName("after merging");
//        user.setName("after merging22222");
//        em.merge(user);
        em.flush();
        user2.setName("after flushing");
        em.clear();

        List<User> users = userRepository.findAll();
//		List<User> users = userRepository.findAllById(Lists.newArrayList(1L, 3L, 5L));
        users.forEach(System.out::println);
    }

    public User givenUser(){
        User user = new User();
        user.setEmail("leehyeonmin34@gmail.com");
        user.setName("이현민");
        return userRepository.save(user);
    }

    @Test
//    @Transactional
    void userAndHistoryRelationTest(){
        User user = givenUser();
        System.out.println(">>>> 저장 마쳤습니다.");
        user.setEmail("fakeEmail@naver.com");
        userRepository.save(user);
        User user2 = userRepository.findById(userRepository.findByName("이현민").stream().findFirst().get().getId()).orElseThrow(RuntimeException::new);

        System.out.println(">>>> 업데이트 마쳤습니다.");
        List<UserHistory> histories = user2.getUserHistories();

//        histories.forEach(System.out::println);
//        System.out.println(">>>> 히스토리 가져왔습니다.");
//        List<UserHistory> histories2 = userHistoryRepository.findAll();
//        histories2.forEach(System.out::println);
//        System.out.println(">>>> 히스토리 가져왔습니다.");

        User user3 = histories.get(1).getUser();
        System.out.println(user3);
        System.out.println(">>>> 유저 가져왔습니다.");


    }

    @Test
    void embeddingTest(){
        User user = givenUser();
        user.setHomeAddress(new Address("사랑시", "고백구", "행복동 36-5", "01232"));
        user.setCompanyAddress(new Address("서울시", "강남구", "최고동 21-2", "22241"));
        User result = userRepository.save(user);
        System.out.println(result);
    }




}
