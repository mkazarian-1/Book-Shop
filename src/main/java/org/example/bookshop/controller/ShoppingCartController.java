package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.cart.ShoppingCartDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.example.bookshop.model.User;
import org.example.bookshop.security.util.UserUtil;
import org.example.bookshop.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart repository manager",
        description = "Endpoints for managing shopping cart's item")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Create book",
            description = """
                    Create cart item object and Returns shopping cart date with
                    list of all available cart items
                    \nNecessary role: USER
                    """)
    public ShoppingCartDto saveItem(
            @RequestBody @Valid CreateCartItemRequestDto cartItemRequestDto) {
        User user = UserUtil.getAuthenticatedUser();
        return shoppingCartService.saveCartItem(cartItemRequestDto, user.getId());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get shopping cart",
            description = """
                    Returns shopping cart date with
                    list of all available cart items
                    \nNecessary role: USER
                    """)
    public ShoppingCartDto getShoppingCart() {
        User user = UserUtil.getAuthenticatedUser();
        return shoppingCartService.getShoppingCart(user.getId());
    }

    @PutMapping("items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update shopping cart",
            description = """
                    Return the update cart item if the update went well.
                    \nNecessary role: USER
                    """)
    public ShoppingCartDto updateCartItem(
            @RequestBody @Valid UpdateCartItemRequestDto cartItemRequestDto,
                                          @PathVariable Long cartItemId) {
        User user = UserUtil.getAuthenticatedUser();
        return shoppingCartService.updateCartItem(cartItemRequestDto, cartItemId, user.getId());
    }

    @DeleteMapping("items/{cartItemId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete shopping cart item",
            description = """
                    Return 204 status if delete went well
                    \nNecessary role: USER
                    """)
    public void deleteCartItem(@PathVariable Long cartItemId) {
        User user = UserUtil.getAuthenticatedUser();
        shoppingCartService.deleteCartItem(cartItemId, user.getId());
    }
}
