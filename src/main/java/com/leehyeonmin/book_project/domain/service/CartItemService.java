package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    public List<CartItemDto> findAllCartItems();

    public CartItemDto findCartItem(Long id);

    public CartItemDto addCartItem(CartItemDto dto);

    public CartItemDto modifyCartItem(CartItemDto dto);

    public Boolean removeCartItem(Long id);
}

