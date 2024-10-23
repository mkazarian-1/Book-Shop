package org.example.bookshop.dto.cart.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequestDto {
    @NotNull
    private Long bookId;
    @NotNull
    @Positive
    private int quantity;
}
