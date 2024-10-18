package org.example.bookshop.dto.cart.item;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestDto {
    @Min(value = 0)
    private int quantity;
}
