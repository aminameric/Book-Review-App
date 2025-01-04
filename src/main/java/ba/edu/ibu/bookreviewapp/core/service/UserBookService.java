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
    public UserBook updateReview(UserBook userBook) {
        System.out.println("Updating Review: " + userBook);

        // Validate that the ID is provided
        if (userBook.getId() == null) {
            throw new IllegalArgumentException("UserBook ID must be provided for update.");
        }

        // Fetch the existing review by ID
        UserBook existingReview = userBookRepository.findById(userBook.getId())
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + userBook.getId()));

        System.out.println("Existing Review: " + existingReview);

        // Update only the fields provided in the request
        if (userBook.getContent() != null) {
            existingReview.setContent(userBook.getContent());
        }
        if (userBook.getRating() != null) {
            existingReview.setRating(userBook.getRating());
        }

        // Save and return the updated review
        UserBook savedReview = userBookRepository.save(existingReview);
        System.out.println("Saved Review: " + savedReview);

        return savedReview;
    }
}
