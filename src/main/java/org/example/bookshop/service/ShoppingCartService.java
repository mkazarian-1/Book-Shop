package org.example.bookshop.service;

import org.example.bookshop.dto.cart.ShoppingCartDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.example.bookshop.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartDto saveCartItem(CreateCartItemRequestDto cartItemRequestDto, Long userId);

    ShoppingCartDto getShoppingCart(Long userId);

    ShoppingCartDto updateCartItem(
            UpdateCartItemRequestDto updateCartItemRequestDto,
            Long cartItemId,
            Long userId
    );

    void deleteCartItem(Long id);
}
