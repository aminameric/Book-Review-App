package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.service.BookService;
import ba.edu.ibu.bookreviewapp.rest.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Book One");
        Book book2 = new Book();
        book2.setTitle("Book Two");

        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById_Found() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Book", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBooksByCategory() {
        Book book = new Book();
        book.setTitle("Category Book");
        Category category = new Category();
        category.setId(1L);

        when(bookService.getBooksByCategory(any(Category.class))).thenReturn(Arrays.asList(book));

        ResponseEntity<List<Book>> response = bookController.getBooksByCategory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Category Book", response.getBody().get(0).getTitle());
        verify(bookService, times(1)).getBooksByCategory(any(Category.class));
    }

    @Test
    void testGetBooksByUserEmail() {
        Book book = new Book();
        book.setTitle("User's Book");

        when(bookService.getBooksByUserEmail("user@example.com")).thenReturn(Arrays.asList(book));

        ResponseEntity<List<Book>> response = bookController.getBooksByUserEmail("user@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("User's Book", response.getBody().get(0).getTitle());
        verify(bookService, times(1)).getBooksByUserEmail("user@example.com");
    }

    @Test
    void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("New Book");
        Book book = new Book();
        book.setTitle("New Book");

        when(bookService.createBook(bookDTO)).thenReturn(book);

        ResponseEntity<Book> response = bookController.createBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Book", response.getBody().getTitle());
        verify(bookService, times(1)).createBook(bookDTO);
    }

    @Test
    void testUpdateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Book");
        Book book = new Book();
        book.setTitle("Updated Book");

        when(bookService.updateBook(1L, bookDTO)).thenReturn(book);

        ResponseEntity<Book> response = bookController.updateBook(1L, bookDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Book", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook(1L, bookDTO);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<String> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book with ID 1 was successfully deleted.", response.getBody());
        verify(bookService, times(1)).deleteBook(1L);
    }
}
