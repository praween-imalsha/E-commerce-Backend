package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.entity.Cart;
import lk.ijse.ecommercebackend.entity.Product;
import lk.ijse.ecommercebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserAndProduct(User user, Product product);
    List<Cart> findByUser_Id(int userId);
    void deleteByUser_Id(int userId);
}
