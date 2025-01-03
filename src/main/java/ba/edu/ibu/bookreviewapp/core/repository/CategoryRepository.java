package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query method to find a category by name
    Optional<Category> findByName(String name);
}

