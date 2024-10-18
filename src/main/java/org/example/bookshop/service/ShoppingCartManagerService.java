package org.example.bookshop.service;

import org.example.bookshop.dto.cart.ShoppingCartDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartManagerService {
    ShoppingCartDto saveCartItem(Pageable pageable, CreateCartItemRequestDto cartItemRequestDto);

    ShoppingCartDto getShoppingCart(Pageable pageable);

    ShoppingCartDto updateCartItem(
            Pageable pageable,
            UpdateCartItemRequestDto updateCartItemRequestDto,
            Long id
    );

    void deleteCartItem(Long id);
}
