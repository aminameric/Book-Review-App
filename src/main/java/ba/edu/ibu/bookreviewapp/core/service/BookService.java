package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.core.repository.CategoryRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository; // Injected UserRepository

    // Constructor with dependency injection
    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository; // Properly initialize userRepository
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getBooksByReadingStatus(Book.ReadingStatus status) {
        return bookRepository.findByReadingStatus(status);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book createBook(BookDTO bookDTO) {
        // Validate title
        if (bookDTO.getTitle() == null || bookDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required.");
        }

        // Validate and fetch the user
        User user = userRepository.findById(bookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookDTO.getUserId()));

        // Handle Category: Existing or New
        Category category;
        if (bookDTO.getCategoryId() != null && bookDTO.getCategoryId() > 0) {
            // Fetch existing Category by ID
            category = categoryRepository.findById(bookDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + bookDTO.getCategoryId()));
        } else if (bookDTO.getCategoryName() != null && !bookDTO.getCategoryName().isEmpty()) {
            // Fetch existing Category by Name or Create New
            category = categoryRepository.findByName(bookDTO.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(bookDTO.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
        } else {
            throw new IllegalArgumentException("Either a valid categoryId or a categoryName must be provided.");
        }

        // Create the book
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setReadingStatus(Book.ReadingStatus.valueOf(bookDTO.getReadingStatus()));
        book.setUser(user); // Set the user
        book.setCategory(category);

        // Save and return the book
        return bookRepository.save(book);
    }


    public List<Book> getBooksByUserEmail(String email) {
        return bookRepository.findBooksByUserEmail(email);
    }




    @Transactional
    public Book updateBook(Long id, BookDTO bookDTO) {
        // Fetch the existing book
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        // Update title if provided
        if (bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty()) {
            book.setTitle(bookDTO.getTitle());
        }

        // Update author if provided
        if (bookDTO.getAuthor() != null && !bookDTO.getAuthor().isEmpty()) {
            book.setAuthor(bookDTO.getAuthor());
        }

        // Update reading status if provided
        if (bookDTO.getReadingStatus() != null) {
            try {
                book.setReadingStatus(Book.ReadingStatus.valueOf(bookDTO.getReadingStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid reading status: " + bookDTO.getReadingStatus());
            }
        }

        // Handle Category
        if (bookDTO.getCategoryId() != null && bookDTO.getCategoryId() > 0) {
            // Update to an existing category
            Category category = categoryRepository.findById(bookDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + bookDTO.getCategoryId()));
            book.setCategory(category);
        } else if (bookDTO.getCategoryName() != null && !bookDTO.getCategoryName().isEmpty()) {
            // Create a new category
            Category category = categoryRepository.findByName(bookDTO.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(bookDTO.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
            book.setCategory(category);
        }

        // Save the updated book
        return bookRepository.save(book);
    }
}
