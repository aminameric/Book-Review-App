package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.Review;
import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.ReviewRepository;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.ReviewDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository; // User repository
    private final BookRepository bookRepository; // Book repository

    // Constructor Dependency Injection
    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    // Fetch all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Fetch a review by ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // Fetch reviews by book
    public List<Review> getReviewsByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    // Fetch reviews by user ID
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Create a new review
    @Transactional
    public Review createReview(ReviewDTO reviewDTO) {
        // Validate ReviewDTO fields
        if (reviewDTO.getContent() == null || reviewDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("Review content is required.");
        }
        if (reviewDTO.getRating() == null || reviewDTO.getRating() < 0 || reviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("Review rating must be between 0 and 5.");
        }

        // Fetch User
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + reviewDTO.getUserId()));

        // Fetch Book
        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + reviewDTO.getBookId()));

        // Create and populate Review
        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setUser(user);
        review.setBook(book);

        // Save Review
        return reviewRepository.save(review);
    }

    // Delete a review by ID
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
