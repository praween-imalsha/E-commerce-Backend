package lk.ijse.ecommercebackend.dto;

import lk.ijse.ecommercebackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private int id;
    private String title;
    private double price;
    private int quantity;
    private String imageName;
    private String image;         // 👈 Added to return full image path
    private int categoryId;
    private String categoryName;


}
