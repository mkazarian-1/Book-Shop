package org.example.bookshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.cart.ShoppingCartDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.example.bookshop.exception.DuplicateException;
import org.example.bookshop.mapper.CartItemMapper;
import org.example.bookshop.mapper.ShoppingCartMapper;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.repository.BookRepository;
import org.example.bookshop.repository.CartItemRepository;
import org.example.bookshop.service.ShoppingCartManagerService;
import org.example.bookshop.service.ShoppingCartService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartManagerServiceImpl implements ShoppingCartManagerService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartService shoppingCartService;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto saveCartItem(
            Pageable pageable, CreateCartItemRequestDto cartItemRequestDto) {
        if (!bookRepository.existsById(cartItemRequestDto.getBookId())) {
            throw new EntityNotFoundException("Can't find Book with id: "
                    + cartItemRequestDto.getBookId());
        }

        if (cartItemRepository.existsByBookId(cartItemRequestDto.getBookId())) {
            throw new DuplicateException("Cart Item with Book with id "
                    + cartItemRequestDto.getBookId() + " already exist");
        }

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByCurrentUser();

        CartItem cartItem = cartItemMapper.toEntity(cartItemRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);

        return getShoppingCart(pageable);
    }

    @Override
    public ShoppingCartDto getShoppingCart(Pageable pageable) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByCurrentUser();
        shoppingCart.setCartItems(new HashSet<>(cartItemRepository
                .findCartItemsByShoppingCart(shoppingCart, pageable)));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateCartItem(
            Pageable pageable,
            UpdateCartItemRequestDto updateCartItemRequestDto, Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no cartItem with the specified ID:" + id));

        cartItemMapper.updateCartItemFromDto(
                cartItem, updateCartItemRequestDto
        );
        cartItemRepository.save(cartItem);

        return getShoppingCart(pageable);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
