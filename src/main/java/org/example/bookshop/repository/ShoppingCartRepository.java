package org.example.bookshop.repository;

import java.util.Optional;
import org.example.bookshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems","cartItems.book","user"})
    Optional<ShoppingCart> findByUserId(Long id);
}
