package org.churchbooks.churchbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(@Autowired BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Operation(summary = "Find all budgets", description = "Returns a list of budgets")
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<Budget> findAll(){return budgetService.findAll();}

    @Operation(summary = "Find a budget by id", description = "Returns the budget with that Id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Budget findById(@PathVariable UUID id){return budgetService.findById(id);}

    @Operation(summary = "Save a budget", description = "Returns the budget created")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Budget save(@RequestBody BudgetDetails budgetDetails){
        return budgetService.save(budgetDetails);
    }

    @Operation(summary = "Edit budget details")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Budget update(@RequestBody BudgetDetails budgetDetails, @PathVariable UUID id){
        return budgetService.update(id, budgetDetails);
    }

}

