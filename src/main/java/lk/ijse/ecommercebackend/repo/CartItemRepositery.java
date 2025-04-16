package lk.ijse.ecommercebackend.repo;


import lk.ijse.ecommercebackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepositery extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
}

