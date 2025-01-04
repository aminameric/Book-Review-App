package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.service.BookService;
import ba.edu.ibu.bookreviewapp.rest.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Book>> getBooksByUserEmail(@RequestParam String email) {
        List<Book> books = bookService.getBooksByUserEmail(email);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok()
                .header("Custom-Header", "Book Deletion Successful")
                .body("Book with ID " + id + " was successfully deleted.");
    }
}
