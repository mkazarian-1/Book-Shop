package org.example.bookshop.repository;

import java.util.List;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.ShoppingCart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByBookId(Long id);

    List<CartItem> findCartItemsByShoppingCart(ShoppingCart shoppingCart, Pageable pageable);
}
