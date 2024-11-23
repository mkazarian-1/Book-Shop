package org.example.bookshop.repository;

import java.util.Optional;
import org.example.bookshop.model.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = "book")
    Optional<OrderItem> findOrderItemByOrderIdAndOrderUserIdAndId(Long orderId,
                                                                  Long userid,
                                                                  Long orderItemId);
}
