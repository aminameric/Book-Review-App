package ba.edu.ibu.bookreviewapp.core.repository;

import ba.edu.ibu.bookreviewapp.core.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Science Fiction");
    }

    @Test
    void testFindByName_Found() {
        // Arrange
        when(categoryRepository.findByName("Science Fiction")).thenReturn(Optional.of(testCategory));

        // Act
        Optional<Category> result = categoryRepository.findByName("Science Fiction");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Science Fiction");
        verify(categoryRepository, times(1)).findByName("Science Fiction");
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        when(categoryRepository.findByName("Unknown Category")).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryRepository.findByName("Unknown Category");

        // Assert
        assertThat(result).isEmpty();
        verify(categoryRepository, times(1)).findByName("Unknown Category");
    }

    @Test
    void testSaveCategory() {
        // Arrange
        when(categoryRepository.save(testCategory)).thenReturn(testCategory);

        // Act
        Category savedCategory = categoryRepository.save(testCategory);

        // Assert
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Science Fiction");
        verify(categoryRepository, times(1)).save(testCategory);
    }

    @Test
    void testDeleteCategory() {
        // Act
        categoryRepository.deleteById(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
