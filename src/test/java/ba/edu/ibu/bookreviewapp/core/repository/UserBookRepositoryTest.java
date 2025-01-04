package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserBookRepositoryTest {

    @Mock
    private UserBookRepository userBookRepository;

    private UserBook testUserBook;
    private User testUser;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");

        testUserBook = new UserBook();
        testUserBook.setId(1L);
        testUserBook.setContent("Amazing book");
        testUserBook.setRating(5.0f);
        testUserBook.setBook(testBook);
        testUserBook.setUser(testUser);
    }

    @Test
    void testFindByUserId() {
        // Arrange
        when(userBookRepository.findByUserId(1L)).thenReturn(Arrays.asList(testUserBook));

        // Act
        List<UserBook> result = userBookRepository.findByUserId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(1L);
        verify(userBookRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testFindByBookId() {
        // Arrange
        when(userBookRepository.findByBookId(1L)).thenReturn(Arrays.asList(testUserBook));

        // Act
        List<UserBook> result = userBookRepository.findByBookId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBook().getId()).isEqualTo(1L);
        verify(userBookRepository, times(1)).findByBookId(1L);
    }

    @Test
    void testFindByBook() {
        // Arrange
        when(userBookRepository.findByBook(testBook)).thenReturn(Arrays.asList(testUserBook));

        // Act
        List<UserBook> result = userBookRepository.findByBook(testBook);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBook().getTitle()).isEqualTo("Test Book");
        verify(userBookRepository, times(1)).findByBook(testBook);
    }

    @Test
    void testFindByBookIdAndUserId_Found() {
        // Arrange
        when(userBookRepository.findByBookIdAndUserId(1L, 1L)).thenReturn(Optional.of(testUserBook));

        // Act
        Optional<UserBook> result = userBookRepository.findByBookIdAndUserId(1L, 1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getRating()).isEqualTo(5.0f);
        verify(userBookRepository, times(1)).findByBookIdAndUserId(1L, 1L);
    }

    @Test
    void testFindByBookIdAndUserId_NotFound() {
        // Arrange
        when(userBookRepository.findByBookIdAndUserId(1L, 2L)).thenReturn(Optional.empty());

        // Act
        Optional<UserBook> result = userBookRepository.findByBookIdAndUserId(1L, 2L);

        // Assert
        assertThat(result).isEmpty();
        verify(userBookRepository, times(1)).findByBookIdAndUserId(1L, 2L);
    }

    @Test
    void testSaveUserBook() {
        // Arrange
        when(userBookRepository.save(testUserBook)).thenReturn(testUserBook);

        // Act
        UserBook savedUserBook = userBookRepository.save(testUserBook);

        // Assert
        assertThat(savedUserBook).isNotNull();
        assertThat(savedUserBook.getContent()).isEqualTo("Amazing book");
        verify(userBookRepository, times(1)).save(testUserBook);
    }

    @Test
    void testDeleteUserBook() {
        // Act
        userBookRepository.deleteById(1L);

        // Assert
        verify(userBookRepository, times(1)).deleteById(1L);
    }
}
