package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    // Find all user-book relations by user ID
    List<UserBook> findByUserId(Long userId);

    // Find all user-book relations by book ID
    List<UserBook> findByBookId(Long bookId);

    List<UserBook> findByBook(Book book);

    Optional<UserBook> findByBookIdAndUserId(Long bookId, Long userId);
}
