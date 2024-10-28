package org.example.bookshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.cart.ShoppingCartDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.example.bookshop.mapper.CartItemMapper;
import org.example.bookshop.mapper.ShoppingCartMapper;
import org.example.bookshop.model.Book;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.BookRepository;
import org.example.bookshop.repository.CartItemRepository;
import org.example.bookshop.repository.ShoppingCartRepository;
import org.example.bookshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto saveCartItem(
            CreateCartItemRequestDto cartItemRequestDto,
            Long userId) {
        Book book = bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Book with id: %d is not found",
                                cartItemRequestDto.getBookId())
                ));

        ShoppingCart cart = getCart(userId);

        cart.getCartItems().stream()
                .filter(item -> item.getBook()
                        .getId()
                        .equals(cartItemRequestDto.getBookId()))
                .findFirst()
                .ifPresentOrElse(item -> item.setQuantity(item.getQuantity()
                                + cartItemRequestDto.getQuantity()),
                        () -> addCartItemToCart(
                                cartItemRequestDto, cart, book));

        shoppingCartRepository.save(cart);
        return getShoppingCart(userId);
    }

    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toDto(getCart(userId));
    }

    @Override
    public ShoppingCartDto updateCartItem(
            UpdateCartItemRequestDto updateCartItemRequestDto, Long cartItemId, Long userId) {
        ShoppingCart cart = getCart(userId);

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId, cart.getId())
                .map(item -> {
                    item.setQuantity(updateCartItemRequestDto.getQuantity());
                    return item;
                }).orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cart item with id: %d for user: %d", cartItemId, userId)
                ));

        cartItemRepository.save(cartItem);
        return getShoppingCart(userId);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    private void addCartItemToCart(CreateCartItemRequestDto itemRequestDto,
                                   ShoppingCart cart, Book book) {
        CartItem cartItem = cartItemMapper.toEntity(itemRequestDto);
        cartItem.setBook(book);
        cartItem.setShoppingCart(cart);
        cart.getCartItems().add(cartItem);
    }

    private ShoppingCart getCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Can't find Shopping cart with user id: %d", userId)));
    }
}
