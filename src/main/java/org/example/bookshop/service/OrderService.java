package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.UpdateOrderRequestDto;
import org.example.bookshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequestDto orderRequestDto, User user);

    Page<OrderDto> getOrderHistory(Pageable pageable, Long userId);

    List<OrderItemDto> getOrderItems(Long orderId, Long userId);

    OrderItemDto getOderItem(Long orderId, Long itemId, Long userId);

    OrderDto updateOrder(Long id, UpdateOrderRequestDto orderRequestDto);

    Page<OrderDto> getAllOrders(Pageable pageable);
}
