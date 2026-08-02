package com.mediclaim.mediclaimpro.claimcategory;

import org.springframework.stereotype.Service;

import java.util.List;

// Contains business logic for creating and retrieving claim categories.
@Service
public class ClaimCategoryService {

    private final ClaimCategoryRepository claimCategoryRepository;

    public ClaimCategoryService(
            ClaimCategoryRepository claimCategoryRepository
    ) {
        this.claimCategoryRepository = claimCategoryRepository;
    }

    // Prevents duplicate category names such as Dental and dental.
    public ClaimCategory createCategory(ClaimCategory category) {
        if (claimCategoryRepository
                .existsByNameIgnoreCase(category.getName())) {

            throw new IllegalArgumentException(
                    "Claim category already exists: " + category.getName()
            );
        }

        return claimCategoryRepository.save(category);
    }

    public List<ClaimCategory> getAllCategories() {
        return claimCategoryRepository.findAll();
    }

    public ClaimCategory getCategoryById(Long id) {
        return claimCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim category not found with id: " + id
                        )
                );
    }
}