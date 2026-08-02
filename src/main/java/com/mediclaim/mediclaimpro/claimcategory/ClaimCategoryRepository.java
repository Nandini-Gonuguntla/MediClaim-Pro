package com.mediclaim.mediclaimpro.claimcategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Handles database operations for medical claim categories.
public interface ClaimCategoryRepository
        extends JpaRepository<ClaimCategory, Long> {

    Optional<ClaimCategory> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}