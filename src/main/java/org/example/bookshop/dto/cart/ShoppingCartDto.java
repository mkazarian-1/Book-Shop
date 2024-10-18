package org.example.bookshop.dto.cart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.dto.cart.item.CartItemDto;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> cartItems;
}
