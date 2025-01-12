package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.repository.UserBookRepository;
import ba.edu.ibu.bookreviewapp.core.repository.UserRepository;
import ba.edu.ibu.bookreviewapp.core.repository.BookRepository;
import ba.edu.ibu.bookreviewapp.rest.dto.UserBookDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserBookService(UserBookRepository userBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<UserBook> getAllUserBooks() {
        return userBookRepository.findAll();
    }

    public Optional<UserBook> getUserBookById(Long id) {
        return userBookRepository.findById(id);
    }

    public List<UserBook> getUserBooksByBookId(Long bookId) {
        return userBookRepository.findByBookId(bookId);
    }

    public List<UserBook> getUserBooksByUserId(Long userId) {
        return userBookRepository.findByUserId(userId);
    }

    @Transactional
    public UserBook createUserBook(UserBookDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + reviewDTO.getUserId()));

        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + reviewDTO.getBookId()));

        UserBook userBook = new UserBook();
        userBook.setContent(reviewDTO.getContent());
        userBook.setRating(reviewDTO.getRating());
        userBook.setUser(user);
        userBook.setBook(book);

        return userBookRepository.save(userBook);
    }

    public void deleteUserBook(Long id) {
        userBookRepository.deleteById(id);
    }

    public Optional<UserBook> getUserBookByBookIdAndUserId(Long bookId, Long userId) {
        return userBookRepository.findByBookIdAndUserId(bookId, userId);
    }

    @Transactional
    public UserBook updateReviewDTO(UserBook existingReview, UserBookDTO reviewDTO) {
        // Ensure the provided review has a valid ID before attempting an update
        if (existingReview.getId() == null) {
            throw new IllegalArgumentException("Review ID must be provided for update.");
        }

        // Update fields only if provided in the DTO
        if (reviewDTO.getContent() != null) {
            existingReview.setContent(reviewDTO.getContent());
        }
        if (reviewDTO.getRating() != null) {
            existingReview.setRating(reviewDTO.getRating());
        }

        return userBookRepository.save(existingReview);
    }
}
