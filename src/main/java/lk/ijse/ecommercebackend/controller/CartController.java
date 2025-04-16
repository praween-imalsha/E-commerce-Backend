package lk.ijse.ecommercebackend.controller;

import lk.ijse.ecommercebackend.entity.Cart;
import lk.ijse.ecommercebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam int userId, @RequestParam int productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @PutMapping("/{cartItemId}/update")
    public ResponseEntity<Cart> updateCart(@PathVariable int cartItemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCartQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/{cartItemId}/remove")
    public ResponseEntity<String> removeCartItem(@PathVariable int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable int userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}