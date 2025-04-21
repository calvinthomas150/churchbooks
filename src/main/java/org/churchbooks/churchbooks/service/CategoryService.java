package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.CategoryDetails;
import org.churchbooks.churchbooks.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    Category save(CategoryDetails categoryDetails);
    Category update(UUID categoryId, CategoryDetails categoryDetails);
}
