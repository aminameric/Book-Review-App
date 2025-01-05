package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Book;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Find all books by category
    List<Book> findByCategory(Category category);

    // Find books by reading status
    List<Book> findByReadingStatus(Book.ReadingStatus readingStatus);

    @Query("SELECT b FROM books b WHERE b.user.email = :email")
    List<Book> findBooksByUserEmail(@Param("email") String email);

}

