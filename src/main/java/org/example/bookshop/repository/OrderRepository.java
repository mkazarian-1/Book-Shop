package org.example.bookshop.repository;

import java.util.Optional;
import org.example.bookshop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    Page<Order> findAllByUserId(Pageable pageable, Long userId);

    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    Page<Order> findAll(Pageable pageable);
}
