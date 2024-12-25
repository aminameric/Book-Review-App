package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Review;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.core.repository.CategoryRepository;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    // Constructor Dependency Injection
    public BookService(BookRepository bookRepository,
                       UserRepository userRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByUser(User user) {
        return bookRepository.findByUser(user);
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
        // Create the Book entity
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        try {
            book.setReadingStatus(Book.ReadingStatus.valueOf(bookDTO.getReadingStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid reading status: " + bookDTO.getReadingStatus());
        }

        // Handle Category: Existing or New
        Category category;
        if (bookDTO.getCategoryId() != null && bookDTO.getCategoryId() > 0) {
            // Fetch existing Category
            category = categoryRepository.findById(bookDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + bookDTO.getCategoryId()));
        } else if (bookDTO.getCategoryName() != null && !bookDTO.getCategoryName().isEmpty()) {
            // Create new Category when categoryId is 0 or null
            category = categoryRepository.findByName(bookDTO.getCategoryName())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(bookDTO.getCategoryName());
                        return categoryRepository.save(newCategory);
                    });
        } else {
            throw new IllegalArgumentException("Either a valid categoryId or a categoryName must be provided.");
        }
        book.setCategory(category);

        // Fetch the User
        User user = userRepository.findById(bookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookDTO.getUserId()));
        book.setUser(user);

        // Save and return the book
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, BookDTO bookDTO) {
        // Fetch the existing book
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        // Update title if provided
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }

        // Update author if provided
        if (bookDTO.getAuthor() != null) {
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

        // Handle Category: Existing or New
        if (bookDTO.getCategoryId() != null && bookDTO.getCategoryId() > 0) {
            // Fetch existing category by ID
            Category category = categoryRepository.findById(bookDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + bookDTO.getCategoryId()));
            book.setCategory(category);
        } else if (bookDTO.getCategoryId() != null && bookDTO.getCategoryId() == 0) {
            // Create new category if `categoryId` is 0 and `categoryName` is provided
            if (bookDTO.getCategoryName() != null && !bookDTO.getCategoryName().isEmpty()) {
                Category category = categoryRepository.findByName(bookDTO.getCategoryName())
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(bookDTO.getCategoryName());
                            return categoryRepository.save(newCategory);
                        });
                book.setCategory(category);
            } else {
                throw new IllegalArgumentException("Category name must be provided when categoryId is 0.");
            }
        }

        // Explicitly ignore `userId` during updates
        if (bookDTO.getUserId() != null) {
            System.out.println("Ignoring userId during update: " + bookDTO.getUserId());
        }

        // Save and return the updated book
        return bookRepository.save(book);
    }







}

