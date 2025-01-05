package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.core.repository.CategoryRepository;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;
    private Category category;
    private User user;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("John Doe");
        book.setReadingStatus(Book.ReadingStatus.READ);

        category = new Category();
        category.setId(1L);
        category.setName("Fiction");

        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("John Doe");
        bookDTO.setReadingStatus("READ");
        bookDTO.setUserId(1L);
        bookDTO.setCategoryId(1L);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();

        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(1L);

        assertThat(result).isEmpty();
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBook_Success() {
        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Valid Title");
        bookDTO.setAuthor("John Doe");
        bookDTO.setReadingStatus("IN_PROGRESS");
        bookDTO.setUserId(1L);
        bookDTO.setCategoryName("Fiction");

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Fiction");

        Book mockBook = new Book();
        mockBook.setTitle("Valid Title");
        mockBook.setAuthor("John Doe");
        mockBook.setReadingStatus(Book.ReadingStatus.IN_PROGRESS);
        mockBook.setUser(mockUser);
        mockBook.setCategory(mockCategory);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(categoryRepository.findByName("Fiction")).thenReturn(Optional.of(mockCategory));
        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        // Act
        Book createdBook = bookService.createBook(bookDTO);

        // Assert
        assertNotNull(createdBook);
        assertEquals("Valid Title", createdBook.getTitle());
        assertEquals("John Doe", createdBook.getAuthor());
    }



    @Test
    void testCreateBook_MissingTitle() {
        bookDTO.setTitle("");

        assertThatThrownBy(() -> bookService.createBook(bookDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title is required.");
    }

    @Test
    void testCreateBook_TitleRequired() {
        // Arrange - Title intentionally left blank
        bookDTO.setTitle("");

        // Act & Assert
        assertThatThrownBy(() -> bookService.createBook(bookDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title is required.");
    }

    @Test
    void testDeleteBook_Success() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateBook_Success() {
        // Arrange
        Long bookId = 1L;
        Long categoryId = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Title");
        bookDTO.setAuthor("Updated Author");
        bookDTO.setReadingStatus("IN_PROGRESS");
        bookDTO.setCategoryId(categoryId);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        Category mockCategory = new Category();
        mockCategory.setId(categoryId);
        mockCategory.setName("Updated Category");

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Original Title");
        existingBook.setAuthor("Original Author");
        existingBook.setReadingStatus(Book.ReadingStatus.NOT_STARTED);
        existingBook.setUser(mockUser);
        existingBook.setCategory(mockCategory);

        // Mocking the necessary repository responses
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        // Act
        Book updatedBook = bookService.updateBook(bookId, bookDTO);

        // Assert
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals(Book.ReadingStatus.IN_PROGRESS, updatedBook.getReadingStatus());
        assertEquals(mockCategory, updatedBook.getCategory());
    }


    @Test
    void testUpdateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(1L, bookDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found with ID: 1");
    }

    @Test
    void testGetBooksByUserEmail_Success() {
        when(bookRepository.findBooksByUserEmail("user@example.com")).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getBooksByUserEmail("user@example.com");

        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findBooksByUserEmail("user@example.com");
    }
}
