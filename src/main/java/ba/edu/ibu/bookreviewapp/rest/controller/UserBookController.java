package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.service.UserBookService;
import ba.edu.ibu.bookreviewapp.rest.dto.UserBookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
