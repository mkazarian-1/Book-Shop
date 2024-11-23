package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.UpdateOrderRequestDto;
import org.example.bookshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);

    default Page<OrderDto> toDtoPage(Page<Order> orders) {
        return orders.map(this::toDto);
    }

    void updateEntity(UpdateOrderRequestDto updateOrderRequestDto,
                      @MappingTarget Order order);
}
