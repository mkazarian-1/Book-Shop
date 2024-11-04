package org.example.bookshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.UpdateOrderRequestDto;
import org.example.bookshop.mapper.OrderItemMapper;
import org.example.bookshop.mapper.OrderMapper;
import org.example.bookshop.model.Order;
import org.example.bookshop.model.OrderItem;
import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.OrderItemRepository;
import org.example.bookshop.repository.OrderRepository;
import org.example.bookshop.repository.ShoppingCartRepository;
import org.example.bookshop.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequestDto orderRequestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find Shopping cart with user id: %d", user.getId()))
                );

        Set<OrderItem> orderItems = orderItemMapper
                .fromCartItemToOrderItem(shoppingCart.getCartItems());
        BigDecimal total = calculateTotalPrice(orderItems);
        Order order = buildOrder(orderRequestDto, user, orderItems, total);

        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderHistory(Pageable pageable, Long userId) {
        return orderMapper.toDtoList(
                orderRepository.findAllByUserId(pageable, userId).toList()
        );
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId, Long userId) {
        return orderItemMapper.toDtoList(orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by orderId: "
                        + orderId
                        + " and userId :"
                        + userId))
                .getOrderItems());
    }

    @Override
    public OrderItemDto getOderItem(Long orderId, Long itemId, Long userId) {
        return orderItemMapper.toDto(orderItemRepository
                .findOrderItemByOrderIdAndOrderUserIdAndId(orderId, userId, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find item from order with id %d and item id %d",
                                orderId, itemId)
                )));
    }

    @Override
    public OrderDto updateOrder(Long id, UpdateOrderRequestDto orderRequestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find order by orderId: " + id));

        if (!isValidStatusTransition(order.getStatus(), orderRequestDto.status())) {
            throw new IllegalArgumentException("Invalid status transition from "
                    + order.getStatus() + " to " + orderRequestDto.status());
        }

        orderMapper.updateEntity(orderRequestDto, order);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Pageable pageable) {
        return orderMapper.toDtoList(orderRepository.findAll(pageable).toList());
    }

    private boolean isValidStatusTransition(Order.Status currentStatus, Order.Status newStatus) {
        return switch (currentStatus) {
            case NEW -> newStatus == Order.Status.CONFIRMED
                    || newStatus == Order.Status.CANCELLED;
            case CONFIRMED -> newStatus == Order.Status.COMPLETED
                    || newStatus == Order.Status.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
    }

    private BigDecimal calculateTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order buildOrder(CreateOrderRequestDto orderRequestDto, User user,
                             Set<OrderItem> orderItems, BigDecimal total) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.NEW);
        order.setTotal(total);

        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);

        return order;
    }
}
