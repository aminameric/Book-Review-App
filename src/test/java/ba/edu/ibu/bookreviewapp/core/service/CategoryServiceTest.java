package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.api.CategorySuggestor;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategorySuggestor categorySuggestor;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        Category category = new Category();
        category.setName("Fiction");

        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        List<Category> result = categoryService.getAllCategories();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Fiction");
        verify(categoryRepository).findAll();
    }

    @Test
    void testGetCategoryById_Found() {
        Category category = new Category();
        category.setName("Science");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Science");
        verify(categoryRepository).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryById(2L);
        assertThat(result).isEmpty();
        verify(categoryRepository).findById(2L);
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Drama");
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.saveCategory(category);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Drama");
        verify(categoryRepository).save(category);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteCategory(1L);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void testSuggestCategory() {
        String title = "Science Fiction";
        when(categorySuggestor.suggestCategory(title)).thenReturn("Science Fiction");

        String result = categoryService.suggestCategory(title);
        assertThat(result).isEqualTo("Science Fiction");
        verify(categorySuggestor).suggestCategory(title);
    }
}
