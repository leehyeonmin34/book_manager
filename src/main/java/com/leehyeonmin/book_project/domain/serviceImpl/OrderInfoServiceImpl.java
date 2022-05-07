package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.Book;
import com.leehyeonmin.book_project.domain.OrderInfo;
import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.dto.OrderInfoDto;
import com.leehyeonmin.book_project.domain.request.OrderInfoUpdateRequest;
import com.leehyeonmin.book_project.domain.request.OrderItemUpdateRequest;
import com.leehyeonmin.book_project.domain.service.OrderInfoService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.OrderInfoRepository;
import com.leehyeonmin.book_project.repository.OrderItemRepository;
import com.leehyeonmin.book_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService {

    private final OrderInfoRepository orderInfoRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ToEntity toEntity;
    private final ToDto toDto;

    @Override
    @Transactional
    public OrderInfoDto addOrderInfo(OrderInfoUpdateRequest orderInfoUpdateRequest){

        if (orderInfoUpdateRequest.getOrderItems() == null || orderInfoUpdateRequest.getOrderItems().size() == 0){
            return null;
        }

        User user = userRepository.findById(orderInfoUpdateRequest.getUserId()).get();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUser(user);
        orderInfo.setOrderDate(now());

        for(OrderItemUpdateRequest orderItemUpdateRequest : orderInfoUpdateRequest.getOrderItems()){
            OrderItem orderItem = new OrderItem();
            Book book = bookRepository.findById(orderItemUpdateRequest.getBookId()).get();
            int ea = orderItemUpdateRequest.getEa();

            orderItem.setEa(ea);
            orderItem.setBook(book);
            orderItem.setOrderInfo(orderInfo);
            orderInfo.addOrderItems(orderItem);
            orderItemRepository.save(orderItem);
        }
        OrderInfo saved = orderInfoRepository.save(orderInfo);

        return new OrderInfoDto(saved);
    }

    @Override
    @Transactional
    public List<OrderInfoDto> findAllOrderInfo() {
        return orderInfoRepository.findAll().stream()
                .map(orderInfo -> new OrderInfoDto(orderInfo)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderInfoDto findOrderInfo(Long id) {
        return new OrderInfoDto(orderInfoRepository.getById(id));
    }

    @Override
    @Transactional
    public Boolean removeOrderInfo(Long id) {
        if(orderInfoRepository.findById(id).isPresent()) {
            orderInfoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public OrderInfoDto modifyOrderInfo(OrderInfoDto orderInfoDto) {
        OrderInfo updated = orderInfoRepository.save(toEntity.from(orderInfoDto));
        return new OrderInfoDto(updated);
    }

}
