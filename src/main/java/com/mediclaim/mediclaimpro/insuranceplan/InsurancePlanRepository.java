package com.mediclaim.mediclaimpro.insuranceplan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Handles database operations for insurance plans.
public interface InsurancePlanRepository
        extends JpaRepository<InsurancePlan, Long> {

    Optional<InsurancePlan> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}