package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderId(Long orderId);
}
