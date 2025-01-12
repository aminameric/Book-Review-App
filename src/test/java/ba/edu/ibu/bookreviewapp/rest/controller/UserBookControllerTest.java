package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.service.UserBookService;
import ba.edu.ibu.bookreviewapp.rest.dto.UserBookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserBookControllerTest {

    @Mock
    private UserBookService userBookService;

    @InjectMocks
    private UserBookController userBookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUserBooks() {
        UserBook userBook1 = new UserBook();
        userBook1.setContent("Great book");
        UserBook userBook2 = new UserBook();
        userBook2.setContent("Awesome read");

        when(userBookService.getAllUserBooks()).thenReturn(Arrays.asList(userBook1, userBook2));

        ResponseEntity<List<UserBook>> response = userBookController.getAllUserBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userBookService, times(1)).getAllUserBooks();
    }

    @Test
    void testGetUserBookById_Found() {
        UserBook userBook = new UserBook();
        userBook.setId(1L);
        userBook.setContent("Interesting book");

        when(userBookService.getUserBookById(1L)).thenReturn(Optional.of(userBook));

        ResponseEntity<UserBook> response = userBookController.getUserBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Interesting book", response.getBody().getContent());
        verify(userBookService, times(1)).getUserBookById(1L);
    }

    @Test
    void testGetUserBookById_NotFound() {
        when(userBookService.getUserBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserBook> response = userBookController.getUserBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userBookService, times(1)).getUserBookById(1L);
    }

    @Test
    void testCreateUserBook() {
        // Creating the UserBookDTO using the constructor
        UserBookDTO userBookDTO = new UserBookDTO(1L, 1L, "Great Story", 4.5f);

        // Creating the UserBook entity to be returned by the service
        UserBook savedUserBook = new UserBook();
        savedUserBook.setId(1L);
        savedUserBook.setContent("Great Story");
        savedUserBook.setRating(4.5f);

        // Mocking the service call
        when(userBookService.createUserBook(any(UserBookDTO.class))).thenReturn(savedUserBook);

        // Performing the test
        ResponseEntity<UserBook> response = (ResponseEntity<UserBook>) userBookController.createUserBook(userBookDTO);

        // Asserting the results
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Great Story", response.getBody().getContent());
        assertEquals(4.5f, response.getBody().getRating());

        // Verifying the service method was called once
        verify(userBookService, times(1)).createUserBook(any(UserBookDTO.class));
    }


    @Test
    void testDeleteUserBook() {
        doNothing().when(userBookService).deleteUserBook(1L);

        ResponseEntity<String> response = userBookController.deleteUserBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UserBook with ID 1 was successfully deleted.", response.getBody());
        verify(userBookService, times(1)).deleteUserBook(1L);
    }

    @Test
    void testGetReviewForBookByUser_Found() {
        UserBook userBook = new UserBook();
        userBook.setId(1L);
        userBook.setContent("Nice Review");

        when(userBookService.getUserBookByBookIdAndUserId(1L, 1L)).thenReturn(Optional.of(userBook));

        ResponseEntity<UserBook> response = userBookController.getReviewForBookByUser(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nice Review", response.getBody().getContent());
        verify(userBookService, times(1)).getUserBookByBookIdAndUserId(1L, 1L);
    }

    @Test
    void testGetReviewForBookByUser_NotFound() {
        when(userBookService.getUserBookByBookIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        ResponseEntity<UserBook> response = userBookController.getReviewForBookByUser(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userBookService, times(1)).getUserBookByBookIdAndUserId(1L, 1L);
    }

    @Test
    void testUpdateReview_Success() {
        UserBook userBook = new UserBook();
        userBook.setId(1L);
        userBook.setContent("Updated Review");
        userBook.setRating(4.5f);

        UserBookDTO reviewDTO = new UserBookDTO();
        reviewDTO.setContent("Updated Review");
        reviewDTO.setRating(4.5f);
        reviewDTO.setUserId(1L);
        reviewDTO.setBookId(1L);

        when(userBookService.getUserBookByBookIdAndUserId(1L, 1L)).thenReturn(Optional.of(userBook));
        when(userBookService.updateReviewDTO(userBook, reviewDTO)).thenReturn(userBook);

        ResponseEntity<?> response = userBookController.updateReview(1L, reviewDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Review", ((UserBook) response.getBody()).getContent());
        verify(userBookService, times(1)).updateReviewDTO(userBook, reviewDTO);
    }

    @Test
    void testUpdateReview_NotFound() {
        UserBookDTO reviewDTO = new UserBookDTO();
        reviewDTO.setUserId(1L);
        reviewDTO.setBookId(1L);
        reviewDTO.setContent("Updated Review");

        when(userBookService.getUserBookByBookIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userBookController.updateReview(1L, reviewDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userBookService, never()).updateReviewDTO(any(UserBook.class), any(UserBookDTO.class));
    }

}
