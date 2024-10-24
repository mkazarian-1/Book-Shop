package org.example.bookshop.mapper;

import java.util.List;
import java.util.Set;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.cart.item.CartItemDto;
import org.example.bookshop.dto.cart.item.CreateCartItemRequestDto;
import org.example.bookshop.dto.cart.item.UpdateCartItemRequestDto;
import org.example.bookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(source = "bookId", target = "book", qualifiedByName = "bookFromId")
    CartItem toEntity(CreateCartItemRequestDto cartItemRequestDto);

    void updateCartItemFromDto(@MappingTarget CartItem cartItem,
                               UpdateCartItemRequestDto requestDto);

    List<CartItemDto> toDtoList(Set<CartItem> cartItems);
}
