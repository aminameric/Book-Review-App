package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.service.CategoryService;
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

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Fiction");
        Category category2 = new Category();
        category2.setName("Non-Fiction");

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category1, category2));

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById_Found() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Science Fiction");

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Science Fiction", response.getBody().getName());
        verify(categoryService, times(1)).getCategoryById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryService, times(1)).getCategoryById(1L);
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setName("Fantasy");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Fantasy");

        when(categoryService.saveCategory(any(Category.class))).thenReturn(savedCategory);

        ResponseEntity<Category> response = categoryController.createCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Fantasy", response.getBody().getName());
        verify(categoryService, times(1)).saveCategory(any(Category.class));
    }

    @Test
    void testSuggestCategory() {
        when(categoryService.suggestCategory("Dune")).thenReturn("Science Fiction");

        ResponseEntity<String> response = categoryController.suggestCategory("Dune");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Science Fiction", response.getBody());
        verify(categoryService, times(1)).suggestCategory("Dune");
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1L);

        ResponseEntity<String> response = categoryController.deleteCategory(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category with ID 1 was successfully deleted.", response.getBody());
        verify(categoryService, times(1)).deleteCategory(1L);
    }
}
