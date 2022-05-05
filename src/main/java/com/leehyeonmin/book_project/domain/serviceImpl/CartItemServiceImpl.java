package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.CartItem;
import com.leehyeonmin.book_project.domain.dto.CartItemDto;
import com.leehyeonmin.book_project.domain.service.CartItemService;
import com.leehyeonmin.book_project.domain.util.ToDto;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItemDto> findAllCartItems() {
        return cartItemRepository.findAll().stream().map(item -> ToDto.from(item)).collect(Collectors.toList());
    }

    @Override
    public CartItemDto findCartItem(Long id) {
        CartItem entity = cartItemRepository.findById(id).orElse(null);
        return ToDto.from(entity);
    }

    @Override
    public CartItemDto addCartItem(CartItemDto dto) {
        CartItem entity = ToEntity.from(dto);
        CartItem saved = cartItemRepository.save(entity);
        return ToDto.from(saved);
    }

    @Override
    public CartItemDto modifyCartItem(CartItemDto dto) {
        if(cartItemRepository.findById(dto.getId()).isPresent()){
            return addCartItem(dto);
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeCartItem(Long id) {
        if(cartItemRepository.findById(id).isPresent()){
            cartItemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
