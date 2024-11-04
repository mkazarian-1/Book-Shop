package org.example.bookshop.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequestDto(@NotBlank String shippingAddress) {

}
