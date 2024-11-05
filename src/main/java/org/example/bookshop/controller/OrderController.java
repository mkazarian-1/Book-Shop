package org.example.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.UpdateOrderRequestDto;
import org.example.bookshop.model.User;
import org.example.bookshop.security.util.UserUtil;
import org.example.bookshop.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Management", description = "Endpoints for managing orders and order items")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create a new order",
            description = """
                    Creates a new order for the current user by current user cart
                    and returns the order details.
                    \nNecessary role: USER
                    """)
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public OrderDto createOrder(@RequestBody @Valid CreateOrderRequestDto orderRequestDto) {
        User user = UserUtil.getAuthenticatedUser();
        return orderService.createOrder(orderRequestDto, user);
    }

    @Operation(summary = "Get order history",
            description = """
                    Returns a paginated list of orders for the current user.
                    \nNecessary role: USER
                    """)
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public Page<OrderDto> getOrderHistory(@PageableDefault(size = 5) Pageable pageable) {
        User user = UserUtil.getAuthenticatedUser();
        return orderService.getOrderHistory(pageable, user.getId());
    }

    @Operation(summary = "Get items in an order",
            description = """
                    Returns a list of items for
                    a specific order of the current user.
                    \nNecessary role: USER
                    """)
    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAuthority('USER')")
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId) {
        User user = UserUtil.getAuthenticatedUser();
        return orderService.getOrderItems(orderId, user.getId());
    }

    @Operation(summary = "Get a specific item in an order",
            description = """
                    Returns details for a specific item
                    in an order for the current user.
                    \nNecessary role: USER
                    """)
    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAuthority('USER')")
    public OrderItemDto getOderItem(@PathVariable Long orderId,
                                    @PathVariable Long itemId) {
        User user = UserUtil.getAuthenticatedUser();
        return orderService.getOderItem(orderId, itemId, user.getId());
    }

    @Operation(summary = "Update an order status",
            description = "Updates the status of an order. Necessary role: ADMIN")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDto updateOrder(@PathVariable Long id,
                                @RequestBody @Valid UpdateOrderRequestDto orderRequestDto) {
        return orderService.updateOrder(id, orderRequestDto);
    }

    @Operation(summary = "Get all order",
            description = """
                    Returns a paginated list of all orders.
                    \nNecessary role: ADMIN
                    """)
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<OrderDto> getAllOrders(@PageableDefault(size = 5) Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }
}
