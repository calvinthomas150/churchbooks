package org.churchbooks.churchbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.churchbooks.churchbooks.dto.CategoryDetails;
import org.churchbooks.churchbooks.entity.Category;
import org.churchbooks.churchbooks.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Find all categories", description = "Returns a list of categories")
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<Category> findAll(){return categoryService.findAll();}

    @Operation(summary = "Save a category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category save(@RequestBody CategoryDetails categoryDetails){
        return categoryService.save(categoryDetails);
    }

    @Operation(summary = "Edit a category total")
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Category update(@PathVariable UUID categoryId, @RequestBody CategoryDetails categoryDetails){
        return categoryService.update(categoryId, categoryDetails);
    }

}
