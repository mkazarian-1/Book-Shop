package org.example.bookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import org.example.bookshop.model.Order;

public record UpdateOrderRequestDto(
        @NotNull Order.Status status) {
}
