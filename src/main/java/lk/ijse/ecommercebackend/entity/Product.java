package lk.ijse.ecommercebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double price;
    private int quantity;

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageName;

    @ManyToOne
    private Category category;

    public String getCategoryName() {
        return (this.category != null) ? this.category.getName() : "Unknown";
    }
}
