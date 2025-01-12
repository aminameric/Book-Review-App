package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.UserBookRepository;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.UserBookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserBookServiceTest {

    @Mock
    private UserBookRepository userBookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UserBookService userBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUserBooks() {
        UserBook userBook = new UserBook();
        when(userBookRepository.findAll()).thenReturn(Collections.singletonList(userBook));

        List<UserBook> result = userBookService.getAllUserBooks();

        assertThat(result).hasSize(1);
        verify(userBookRepository).findAll();
    }

    @Test
    void testGetUserBookById_Found() {
        UserBook userBook = new UserBook();
        when(userBookRepository.findById(1L)).thenReturn(Optional.of(userBook));

        Optional<UserBook> result = userBookService.getUserBookById(1L);

        assertThat(result).isPresent();
        verify(userBookRepository).findById(1L);
    }

    @Test
    void testGetUserBookById_NotFound() {
        when(userBookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserBook> result = userBookService.getUserBookById(1L);

        assertThat(result).isEmpty();
        verify(userBookRepository).findById(1L);
    }

    @Test
    void testCreateUserBook_Success() {
        UserBookDTO userBookDTO = new UserBookDTO(1L, 1L, "Great Book", 5);
        User user = new User();
        Book book = new Book();
        UserBook userBook = new UserBook();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userBookRepository.save(any(UserBook.class))).thenReturn(userBook);

        UserBook result = userBookService.createUserBook(userBookDTO);

        assertThat(result).isNotNull();
        verify(userRepository).findById(1L);
        verify(bookRepository).findById(1L);
        verify(userBookRepository).save(any(UserBook.class));
    }

    @Test
    void testCreateUserBook_UserNotFound() {
        UserBookDTO userBookDTO = new UserBookDTO(1L, 1L, "Great Book", 5);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userBookService.createUserBook(userBookDTO));
        verify(userRepository).findById(1L);
    }

    @Test
    void testDeleteUserBook() {
        doNothing().when(userBookRepository).deleteById(1L);

        userBookService.deleteUserBook(1L);

        verify(userBookRepository).deleteById(1L);
    }

    @Test
    void testUpdateUserBook_Success() {
        UserBook userBook = new UserBook();
        userBook.setId(1L);
        userBook.setContent("Old Content");
        userBook.setRating(3.0F);

        UserBookDTO reviewDTO = new UserBookDTO();
        reviewDTO.setContent("Updated Content");
        reviewDTO.setRating(4F);

        when(userBookRepository.findById(1L)).thenReturn(Optional.of(userBook));
        when(userBookRepository.save(any(UserBook.class))).thenReturn(userBook);

        UserBook result = userBookService.updateReviewDTO(userBook, reviewDTO);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("Updated Content");
        assertThat(result.getRating()).isEqualTo(4F);
        verify(userBookRepository).save(any(UserBook.class));
    }

    @Test
    void testUpdateUserBook_MissingId() {
        UserBook userBook = new UserBook();  // No ID set
        UserBookDTO reviewDTO = new UserBookDTO();
        reviewDTO.setContent("Content Missing ID");

        assertThrows(IllegalArgumentException.class,
                () -> userBookService.updateReviewDTO(userBook, reviewDTO));

        verify(userBookRepository, never()).save(any(UserBook.class));
    }

    @Test
    void testUpdateUserBook_NotFound() {
        UserBook userBook = new UserBook();
        userBook.setId(null);  // Simulating missing ID
        UserBookDTO reviewDTO = new UserBookDTO();
        reviewDTO.setContent("Updated Content");

        // Expecting an exception due to the missing ID
        assertThrows(IllegalArgumentException.class,
                () -> userBookService.updateReviewDTO(userBook, reviewDTO));

        verify(userBookRepository, never()).save(any(UserBook.class));
    }
}
