package lk.ijse.ecommercebackend.service;


import lk.ijse.ecommercebackend.dto.ReviewDTO;
import lk.ijse.ecommercebackend.entity.Review;
import lk.ijse.ecommercebackend.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void submitReview(Review review) {
        review.setApproved(false);
        reviewRepository.save(review);
    }

    public List<Review> getApprovedReviewsForProduct(int productId) {
        return reviewRepository.findByProductIdAndApprovedTrue(productId);
    }

    public List<ReviewDTO> findPendingReviews() {
        return reviewRepository.findByApprovedFalse()
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }

    public Review approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(true);
        return reviewRepository.save(review);
    }

    public boolean rejectReview(Long reviewId) {
        return reviewRepository.findById(reviewId).map(review -> {
            reviewRepository.delete(review);
            return true;
        }).orElse(false);
    }

    public List<ReviewDTO> getApprovedReviews() {
        return reviewRepository.findByApprovedTrue()
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }
}
