package lk.ijse.ecommercebackend.controller;


import lk.ijse.ecommercebackend.dto.OrderDTO;
import lk.ijse.ecommercebackend.entity.Order;
import lk.ijse.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(@PathVariable int userId) {
        try {
            Order order = orderService.checkout(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unable to checkout. " + e.getMessage());
        }
    }
    @GetMapping("all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getUserOrdersForHistory(@PathVariable int userId) {
        List<Order> orders = orderService.getUserOrdersForHistory(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalOrders() {
        long count = orderService.getTotalOrders();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/pending")
    public ResponseEntity<Long> getPendingOrdersCount() {
        long count = orderService.getPendingOrdersCount();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable int userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status/{newStatus}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId, @PathVariable String newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Order cannot be deleted.");
        }
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable int orderId) {
        try {
            OrderDTO order = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> getOrderStatus(@PathVariable int orderId) {
        try {
            OrderDTO status = orderService.getOrderStatus(orderId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
