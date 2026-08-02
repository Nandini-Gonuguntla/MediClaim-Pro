package com.mediclaim.mediclaimpro.claimcategory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Exposes REST endpoints for medical claim categories.
@RestController
@RequestMapping("/api/claim-categories")
public class ClaimCategoryController {

    private final ClaimCategoryService claimCategoryService;

    public ClaimCategoryController(
            ClaimCategoryService claimCategoryService
    ) {
        this.claimCategoryService = claimCategoryService;
    }

    // Creates a new medical claim category.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimCategory createCategory(
            @RequestBody ClaimCategory category
    ) {
        return claimCategoryService.createCategory(category);
    }

    @GetMapping
    public List<ClaimCategory> getAllCategories() {
        return claimCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ClaimCategory getCategoryById(
            @PathVariable Long id
    ) {
        return claimCategoryService.getCategoryById(id);
    }
}