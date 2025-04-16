package lk.ijse.ecommercebackend.entity;

import jakarta.persistence.*;
import lk.ijse.ecommercebackend.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int rating;
    private String comment;
    private boolean approved = false;
    private LocalDateTime date = LocalDateTime.now();
}
