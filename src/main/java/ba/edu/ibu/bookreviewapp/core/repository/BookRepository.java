package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.User;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query method to find all books by a user
    List<Book> findByUser(User user);

    // Custom query method to find all books by category
    List<Book> findByCategory(Category category);

    // Custom query method to find books by reading status
    List<Book> findByReadingStatus(Book.ReadingStatus readingStatus);

    List<Book> findBookByUser_Email(String email);
}

