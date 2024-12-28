package ba.edu.ibu.bookreviewapp.core.service;

import ba.edu.ibu.bookreviewapp.core.api.categorysuggestor.CategorySuggestor;
import ba.edu.ibu.bookreviewapp.core.model.Category;
import ba.edu.ibu.bookreviewapp.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySuggestor categorySuggestor;

    public CategoryService(CategoryRepository categoryRepository, CategorySuggestor categorySuggestor) {
        this.categoryRepository = categoryRepository;
        this.categorySuggestor = categorySuggestor;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public String suggestCategory(String title) {
        return categorySuggestor.suggestCategory(title);
    }
}
