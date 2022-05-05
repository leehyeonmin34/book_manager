package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.common.BaseControllerTest;
import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.request.OrderInfoUpdateRequest;
import com.leehyeonmin.book_project.domain.request.OrderItemUpdateRequest;
import com.leehyeonmin.book_project.domain.serviceImpl.OrderInfoServiceImpl;
import com.leehyeonmin.book_project.domain.serviceImpl.OrderItemServiceImpl;
import com.leehyeonmin.book_project.domain.serviceImpl.UserServiceImpl;
import com.leehyeonmin.book_project.repository.BookRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("주문 정보 api 테스트")
public class OrderInfoControllerTest extends BaseControllerTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderInfoServiceImpl orderInfoService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    UserServiceImpl userService;

    @Test
    @DisplayName("주문 저장(성공)")
    void addOrderInfoSuccess() throws Exception {
        //GIVEN

        Book book1 = new Book();
        book1.setName("책1");
        Book book2 = new Book();
        book1.setName("책2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        OrderItemUpdateRequest orderItemUpdateRequest1 = OrderItemUpdateRequest.builder()
                .bookId(1L)
                .ea(2)
                .price(20000)
                .build();
        OrderItemUpdateRequest orderItemUpdateRequest2 = OrderItemUpdateRequest.builder()
                .bookId(2L)
                .ea(1)
                .price(15000)
                .build();
        OrderInfoUpdateRequest orderInfoUpdateRequest = new OrderInfoUpdateRequest();
        orderInfoUpdateRequest.setOrderItems(Lists.list(orderItemUpdateRequest1, orderItemUpdateRequest2));
        orderInfoUpdateRequest.setTotalPrice(35000);
        orderInfoUpdateRequest.setTotalNum(3);
        orderInfoUpdateRequest.setUserId(1L);

        //WHEN
        ResultActions resultActions = this.mockMvc.perform(post("/api/order_info/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(orderInfoUpdateRequest))
        );

        //THEN
        resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andDo(MockMvcResultHandlers.print());
        orderInfoService.findAllOrderInfo().forEach(System.out::println);
        orderItemService.findAllOrderItem().forEach(System.out::println);
    }

    @Test
    @DisplayName("주문 저장(실패) - 주문 정보 없음")
    void addOrderInfoFail() throws Exception {

        //GIVEN
        OrderInfoUpdateRequest orderInfoUpdateRequest = new OrderInfoUpdateRequest();
        orderInfoUpdateRequest.setOrderItems(null);
        orderInfoUpdateRequest.setTotalPrice(0);
        orderInfoUpdateRequest.setTotalNum(0);
        orderInfoUpdateRequest.setUserId(1L);


        //WHEN
        ResultActions resultActions = this.mockMvc.perform(post("/api/order_info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(orderInfoUpdateRequest))
        );

        //THEN
        resultActions.andExpect(status().is4xxClientError());
    }


}
