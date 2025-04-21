package org.churchbooks.churchbooks.repository;

import org.churchbooks.churchbooks.entity.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, UUID> {
}
