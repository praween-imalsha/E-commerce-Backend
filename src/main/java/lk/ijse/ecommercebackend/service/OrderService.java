package lk.ijse.ecommercebackend.service;

import jakarta.transaction.Transactional;
import lk.ijse.ecommercebackend.dto.OrderDTO;
import lk.ijse.ecommercebackend.entity.Cart;
import lk.ijse.ecommercebackend.entity.Order;
import lk.ijse.ecommercebackend.entity.OrderItem;
import lk.ijse.ecommercebackend.entity.Product;
import lk.ijse.ecommercebackend.repo.*;
import lk.ijse.ecommercebackend.user.OrderStatus;
import lk.ijse.ecommercebackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepo productRepository;

    @Transactional

    public Order checkout(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser_Id(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalPrice = cartItems.stream()
                .mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getPrice())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        Order finalOrder = order;

        List<OrderItem> orderItems = cartItems.stream().map(cart -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(finalOrder);
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(cart.getQuantity() * cart.getProduct().getPrice());

            Product product = cart.getProduct();
            if (product.getQuantity() < cart.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getTitle());
            }
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productRepository.save(product);

            return orderItem;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        cartRepository.deleteByUser_Id(userId);

        return order;
    }
    public List<Order> getUserOrdersForHistory(int userId) {
        return orderRepository.findByUser_Id(userId);
    }
    public List<Order> getUserOrders(int userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public Order updateOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status. Allowed: PENDING, SHIPPED, CANCELLED.");
        }

        return orderRepository.save(order);
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public long getPendingOrdersCount() {
        return orderRepository.countByStatus(OrderStatus.PENDING);
    }

    public OrderDTO getOrderStatus(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderDTO(order);
    }
    public boolean deleteOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.CANCELLED) {
                orderRepository.deleteById(orderId);
                return true;
            }
        }
        return false;
    }
    public OrderDTO getOrderDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderDTO(order);
    }
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDTO::new) // Convert to DTO
                .collect(Collectors.toList());    }

}
