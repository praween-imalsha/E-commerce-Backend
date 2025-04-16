package lk.ijse.ecommercebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private int id;
    private int userId;
    private ProductDTO product;
    private int quantity;
    private double totalPrice;
}
