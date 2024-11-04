package org.example.bookshop.mapper;

import java.util.List;
import java.util.Set;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "book.price", target = "price")
    OrderItem cartItemToOrderItem(CartItem cartItem);

    List<OrderItemDto> toDtoList(Set<OrderItem> orderItems);

    Set<OrderItem> fromCartItemToOrderItem(Set<CartItem> cartItems);
}
