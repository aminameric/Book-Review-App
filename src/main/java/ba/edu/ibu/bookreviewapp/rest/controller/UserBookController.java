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

    @PostMapping
    public ResponseEntity<UserBook> createUserBook(@RequestBody UserBookDTO reviewDTO) {
        UserBook savedUserBook = userBookService.createUserBook(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
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

    @PutMapping("/{id}")
    public ResponseEntity<UserBook> updateReview(
            @PathVariable Long id,
            @RequestBody UserBook userBook
    ) {
        // Log the incoming data
        System.out.println("Path ID: " + id);
        System.out.println("Request Body ID: " + userBook.getId());
        System.out.println("Request Body Content: " + userBook.getContent());
        System.out.println("Request Body Rating: " + userBook.getRating());

        // Validate the ID in the path matches the ID in the body
        if (userBook.getId() == null || !id.equals(userBook.getId())) {
            System.out.println("ID Mismatch: Path ID does not match UserBook ID");
            return ResponseEntity.badRequest().build();
        }

        UserBook updatedReview = userBookService.updateReview(userBook);
        return ResponseEntity.ok(updatedReview);
    }


}
