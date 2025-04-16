package lk.ijse.ecommercebackend.dto;

import lk.ijse.ecommercebackend.entity.Order;
import lk.ijse.ecommercebackend.user.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private String customerName;
    private String productTitle;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus status;


    public OrderDTO(Order order) {
        this.id = order.getId();
        this.customerName = order.getUser().getUsername();
        this.productTitle = order.getOrderItems().get(0).getProduct().getTitle(); // Get product title
        this.quantity = order.getOrderItems().get(0).getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}
