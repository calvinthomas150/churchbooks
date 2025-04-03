package org.churchbooks.churchbooks.transactions.services;

import org.churchbooks.churchbooks.transactions.dtos.CategoryDetails;
import org.churchbooks.churchbooks.transactions.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    Category save(CategoryDetails categoryDetails);
    Category update(UUID categoryId, CategoryDetails categoryDetails);
}
