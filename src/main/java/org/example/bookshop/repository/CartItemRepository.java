package org.example.bookshop.repository;

import java.util.Optional;
import org.example.bookshop.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"book","shoppingCart"})
    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long cartId);
}
