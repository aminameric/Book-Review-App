package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.service.UserBookService;
import ba.edu.ibu.bookreviewapp.rest.dto.UserBookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user-books")
public class UserBookController {

    private final UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @GetMapping
    public ResponseEntity<List<UserBook>> getAllUserBooks() {
        return ResponseEntity.ok(userBookService.getAllUserBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBook> getUserBookById(@PathVariable Long id) {
        Optional<UserBook> userBook = userBookService.getUserBookById(id);
        return userBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserBook(@PathVariable Long id) {
        userBookService.deleteUserBook(id);
        return ResponseEntity.ok()
                .header("Custom-Header", "UserBook Deletion Successful")
                .body("UserBook with ID " + id + " was successfully deleted.");
    }

    @GetMapping("/review")
    public ResponseEntity<UserBook> getReviewForBookByUser(
            @RequestParam Long bookId,
            @RequestParam Long userId
    ) {
        Optional<UserBook> userBook = userBookService.getUserBookByBookIdAndUserId(bookId, userId);
        return userBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUserBook(@RequestBody UserBookDTO reviewDTO) {
        // Validate the provided data
        if (reviewDTO.getUserId() == null || reviewDTO.getBookId() == null || reviewDTO.getRating() == null) {
            return ResponseEntity.badRequest().body("User ID, Book ID, and Rating must be provided.");
        }

        try {
            UserBook savedUserBook = userBookService.createUserBook(reviewDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create review: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @RequestBody UserBookDTO reviewDTO
    ) {
        // Validate input data
        if (reviewDTO.getUserId() == null || reviewDTO.getBookId() == null) {
            return ResponseEntity.badRequest().body("User ID and Book ID must be provided.");
        }

        // Attempt to update the review
        Optional<UserBook> existingReview = userBookService.getUserBookByBookIdAndUserId(reviewDTO.getBookId(), reviewDTO.getUserId());
        if (existingReview.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }

        try {
            UserBook updatedReview = userBookService.updateReviewDTO(existingReview.get(), reviewDTO);
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update review: " + e.getMessage());
        }
    }
}
