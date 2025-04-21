package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.CategoryDetails;
import org.churchbooks.churchbooks.entity.Category;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(CategoryDetails categoryDetails) {
        return categoryRepository.save(new Category(categoryDetails.name(), categoryDetails.amount()));
    }

    @Override
    public Category update(UUID categoryId, CategoryDetails categoryDetails) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found. Id: " + categoryId));

        return categoryRepository.save(category.updateCategory(categoryDetails));
    }

}
