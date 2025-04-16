package lk.ijse.ecommercebackend.dto;

import lk.ijse.ecommercebackend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private int productId; // Product ID
    private String productTitle;
    private String userName;
    private Integer rating;
    private String comment;
    private boolean approved;
    private LocalDateTime date;


    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.productId = review.getProduct().getId(); // Get product ID
        this.productTitle = review.getProduct().getTitle(); // Get product title
        this.userName = review.getUser().getUsername(); // Get user name
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.approved = review.isApproved();
        this.date = review.getDate();
    }
}
