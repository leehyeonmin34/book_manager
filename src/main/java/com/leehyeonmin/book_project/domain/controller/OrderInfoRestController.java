package com.leehyeonmin.book_project.domain.controller;

import com.leehyeonmin.book_project.domain.dto.OrderInfoDto;
import com.leehyeonmin.book_project.domain.request.OrderInfoUpdateRequest;
import com.leehyeonmin.book_project.domain.serviceImpl.BookServiceImpl;
import com.leehyeonmin.book_project.domain.serviceImpl.OrderInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order_info")
public class OrderInfoRestController {

    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Autowired
    private BookServiceImpl bookService;



//    @PostMapping("/")
//    public ResponseEntity<OrderInfoDto> addOrderInfo(@RequestBody OrderInfoUpdateRequest orderInfoUpdateRequest){
//        OrderInfoDto orderInfoDtoResponse = orderInfoService.addOrderInfo(orderInfoUpdateRequest);
//        if(orderInfoDtoResponse == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderInfoDtoResponse);
//        }
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderInfoDtoResponse);
//    }

}
