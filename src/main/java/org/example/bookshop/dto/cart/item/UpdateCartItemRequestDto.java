package org.example.bookshop.dto.cart.item;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestDto {
    @Positive
    private int quantity;
}
