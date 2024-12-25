package ba.edu.ibu.bookreviewapp.rest.controller;

import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.model.Review;
import ba.edu.ibu.bookreviewapp.core.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/suggest")
    public ResponseEntity<String> suggestCategory(@RequestParam String title) {
        String category = categoryService.suggestCategory(title);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        // Check if a category with the same name exists
        Optional<Category> existingCategory = categoryService.getCategoryByName(category.getName());
        if (existingCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingCategory.get());
        }

        // Reset id if it's 0
        if (category.getId() != null && category.getId() == 0) {
            category.setId(null);
        }

        // Save the category
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with ID " + id + " was successfully deleted.");
    }
}
