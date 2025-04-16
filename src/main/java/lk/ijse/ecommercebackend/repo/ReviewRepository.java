package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductIdAndApprovedTrue(int productId);
    List<Review> findByApprovedTrue();
    List<Review> findByApprovedFalse();
}
