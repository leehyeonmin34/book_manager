package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.OrderItem;
import com.leehyeonmin.book_project.domain.dto.OrderItemDto;
import com.leehyeonmin.book_project.domain.service.OrderItemService;
import com.leehyeonmin.book_project.repository.BookRepository;
import com.leehyeonmin.book_project.repository.OrderInfoRepository;
import com.leehyeonmin.book_project.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    final private OrderItemRepository orderItemRepository;

    final private BookRepository bookRepository;

    final private ModelMapper modelMapper;

    final private OrderInfoRepository orderInfoRepository;

    @Override
    public List<OrderItemDto> findAllOrderItem() {
        return orderItemRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, OrderItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }


    @Override
    public void modifyOrderItem(OrderItemDto orderItemDto) {
        OrderItem previous = orderItemRepository.findById(orderItemDto.getId()).get();
        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
        orderItem.setOrderInfo(previous.getOrderInfo());
    }

}
