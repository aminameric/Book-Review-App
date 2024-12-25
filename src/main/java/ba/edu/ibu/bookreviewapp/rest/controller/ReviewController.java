package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.Review;
import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.service.ReviewService;
import ba.edu.ibu.bookreviewapp.rest.dto.ReviewDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
        System.out.println("Received ReviewDTO: " + reviewDTO);

        // Delegate to the service layer
        Review savedReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id); // Assuming this deletes the review
        return ResponseEntity.ok()
                .header("Custom-Header", "Review Deleted Successfully")
                .body("Review with ID " + id + " was successfully deleted.");
    }


}
