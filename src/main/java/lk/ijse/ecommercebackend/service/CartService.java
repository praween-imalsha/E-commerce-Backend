package lk.ijse.ecommercebackend.service;

import lk.ijse.ecommercebackend.entity.Cart;
import lk.ijse.ecommercebackend.entity.Product;
import lk.ijse.ecommercebackend.repo.CartRepository;
import lk.ijse.ecommercebackend.repo.ProductRepo;
import lk.ijse.ecommercebackend.repo.UserRepository;
import lk.ijse.ecommercebackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepo productRepository;

    public Cart addToCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cartItem = cartRepository.findByUserAndProduct(user, product);
        if (cartItem == null) {
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(product.getPrice() * quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getQuantity() * product.getPrice());
        }

        return cartRepository.save(cartItem);
    }

    public List<Cart> getUserCart(int userId) {
        return cartRepository.findByUser_Id(userId);
    }

    public Cart updateCartQuantity(int cartItemId, int quantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * cartItem.getProduct().getPrice());
        return cartRepository.save(cartItem);
    }

    public void removeCartItem(int cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public void clearCart(int userId) {
        cartRepository.deleteByUser_Id(userId);
    }
}
