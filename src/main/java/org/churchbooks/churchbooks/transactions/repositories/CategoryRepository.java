package org.churchbooks.churchbooks.transactions.repositories;

import org.churchbooks.churchbooks.transactions.entities.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, UUID> {
}
