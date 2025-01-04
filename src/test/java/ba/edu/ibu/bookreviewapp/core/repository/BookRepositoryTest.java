package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Fiction");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setCategory(category);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setCategory(category);
    }

    @Test
    void testFindByCategory() {
        when(bookRepository.findByCategory(category)).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookRepository.findByCategory(category);

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findByCategory(category);
    }

    @Test
    void testFindByReadingStatus() {
        when(bookRepository.findByReadingStatus(Book.ReadingStatus.IN_PROGRESS))
                .thenReturn(List.of(book1));

        List<Book> books = bookRepository.findByReadingStatus(Book.ReadingStatus.IN_PROGRESS);

        assertEquals(1, books.size());
        assertEquals("Book One", books.get(0).getTitle());
        verify(bookRepository, times(1)).findByReadingStatus(Book.ReadingStatus.IN_PROGRESS);
    }

    @Test
    void testFindBooksByUserEmail() {
        String email = "user@example.com";
        when(bookRepository.findBooksByUserEmail(email)).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookRepository.findBooksByUserEmail(email);

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findBooksByUserEmail(email);
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book savedBook = bookRepository.save(book1);

        assertNotNull(savedBook);
        assertEquals("Book One", savedBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testFindById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Optional<Book> foundBook = bookRepository.findById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("Book One", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }
}
