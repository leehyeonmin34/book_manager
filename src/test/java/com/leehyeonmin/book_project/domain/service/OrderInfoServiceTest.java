package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.dto.OrderInfoDto;
import com.leehyeonmin.book_project.domain.request.OrderInfoUpdateRequest;
import com.leehyeonmin.book_project.domain.request.OrderItemUpdateRequest;
import com.leehyeonmin.book_project.domain.serviceImpl.OrderInfoServiceImpl;
import com.leehyeonmin.book_project.domain.serviceImpl.OrderItemServiceImpl;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.OrderInfoRepository;
import com.leehyeonmin.book_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
public class OrderInfoServiceTest {

    @Autowired
    OrderInfoServiceImpl orderInfoService;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Test
    public void getAllOrderInfoTest(){
        OrderInfoDto orderInfoDto = givenOrderInfoDto();

        System.out.println(">>>>>>>>>");
        orderInfoService.findAllOrderInfo().forEach(System.out::println);

    }

    public OrderInfoDto givenOrderInfoDto(){
        OrderInfo orderInfo = new OrderInfo();
        OrderInfo saved = orderInfoRepository.save(orderInfo);
        return modelMapper.map(saved, OrderInfoDto.class);
    }

    @Transactional
    @Test
    public void deleteTest() {
        //WHEN
        User user = new User();
        userRepository.save(user);

        Book book1 = new Book();
        bookRepository.save(book1);

        Book book2 = new Book();
        bookRepository.save(book2);


        OrderItemUpdateRequest orderItemUpdateRequest1 = new OrderItemUpdateRequest();
        orderItemUpdateRequest1.setBookId(1L);
        OrderItemUpdateRequest orderItemUpdateRequest2 = new OrderItemUpdateRequest();
        orderItemUpdateRequest2.setBookId(2L);
        OrderInfoUpdateRequest orderInfoUpdateRequest = new OrderInfoUpdateRequest();
        orderInfoUpdateRequest.setUserId(1L);
        orderInfoUpdateRequest.setOrderItems(List.of(orderItemUpdateRequest1, orderItemUpdateRequest2));


        //DO
        orderInfoService.addOrderInfo(orderInfoUpdateRequest);
        orderInfoService.removeOrderInfo(1L);

        //THEN
        System.out.println(">>>>>>>>>>");
        orderInfoService.findAllOrderInfo().forEach(System.out::println);
        orderItemService.findAllOrderItem().forEach(System.out::println);
        userRepository.findById(6L).get().getOrderInfos().forEach(System.out::println);
    }

    @Test
    public void updateTest() {
        OrderInfoDto orderInfoDto1 = givenOrderInfoDto();
        OrderInfoDto orderInfoDto2 = givenOrderInfoDto();
        orderInfoDto2.setUserId(1L);

        orderInfoService.modifyOrderInfo(modelMapper.map(orderInfoDto2, OrderInfoDto.class));
        System.out.println(">>>>>>>>>>");
        orderInfoService.findAllOrderInfo().forEach(System.out::println);
    }

    @Test
    public void addTest() {
        //WHEN
        User user = new User();
        userRepository.save(user);

        Book book = new Book();
        bookRepository.save(book);

        OrderItemUpdateRequest orderItemUpdateRequest = new OrderItemUpdateRequest();
        orderItemUpdateRequest.setBookId(1L);
        OrderInfoUpdateRequest orderInfoUpdateRequest = new OrderInfoUpdateRequest();
        orderInfoUpdateRequest.setUserId(1L);
        orderInfoUpdateRequest.setOrderItems(List.of(orderItemUpdateRequest));


        //DO
        orderInfoService.addOrderInfo(orderInfoUpdateRequest);

        //THEN
        System.out.println(">>>>>>>>>>");
        orderInfoService.findAllOrderInfo().forEach(System.out::println);
        orderItemService.findAllOrderItem().forEach(System.out::println);

    }

    @Test
    public void deleteOrderItemTest() {
        //WHEN
        User user = new User();
        userRepository.save(user);

        Book book1 = new Book();
        bookRepository.save(book1);

        Book book2 = new Book();
        bookRepository.save(book2);


        OrderItemUpdateRequest orderItemUpdateRequest1 = new OrderItemUpdateRequest();
        orderItemUpdateRequest1.setBookId(1L);
        OrderItemUpdateRequest orderItemUpdateRequest2 = new OrderItemUpdateRequest();
        orderItemUpdateRequest2.setBookId(2L);
        OrderInfoUpdateRequest orderInfoUpdateRequest = new OrderInfoUpdateRequest();
        orderInfoUpdateRequest.setUserId(1L);
        orderInfoUpdateRequest.setOrderItems(List.of(orderItemUpdateRequest1, orderItemUpdateRequest2));


        //DO
        orderInfoService.addOrderInfo(orderInfoUpdateRequest);
        orderItemService.removeOrderItem(1L);

        //THEN
        System.out.println(">>>>>>>>>>");
        orderInfoService.findAllOrderInfo().forEach(System.out::println);
        orderItemService.findAllOrderItem().forEach(System.out::println);
    }




}
