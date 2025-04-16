package lk.ijse.ecommercebackend.controller;

import lk.ijse.ecommercebackend.dto.ReviewDTO;
import lk.ijse.ecommercebackend.entity.Review;
import lk.ijse.ecommercebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(@RequestBody Review review) {
        try {
            reviewService.submitReview(review);
            return ResponseEntity.ok("Review submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting review: " + e.getMessage());
        }
    }

    @GetMapping("/approved/{productId}")
    public ResponseEntity<List<Review>> getApprovedReviews(@PathVariable int productId) {
        List<Review> reviews = reviewService.getApprovedReviewsForProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ReviewDTO>> getApprovedReviews() {
        return ResponseEntity.ok(reviewService.getApprovedReviews());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReviewDTO>> getPendingReviews() {
        return ResponseEntity.ok(reviewService.findPendingReviews());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Review> approveReview(@PathVariable Long id) {
        Review approvedReview = reviewService.approveReview(id);
        return ResponseEntity.ok(approvedReview);
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<String> rejectReview(@PathVariable Long id) {
        boolean deleted = reviewService.rejectReview(id);
        if (deleted) {
            return ResponseEntity.ok("Review rejected successfully.");
        } else {
            return ResponseEntity.status(404).body("Review not found.");
        }
    }
}
