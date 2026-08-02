package com.mediclaim.mediclaimpro.plancoverage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Handles database operations for category-wise insurance coverage limits.
public interface PlanCoverageRepository
        extends JpaRepository<PlanCoverage, Long> {

    List<PlanCoverage> findByInsurancePlanId(Long insurancePlanId);

    Optional<PlanCoverage>
    findByInsurancePlanIdAndClaimCategoryId(
            Long insurancePlanId,
            Long claimCategoryId
    );

    boolean existsByInsurancePlanIdAndClaimCategoryId(
            Long insurancePlanId,
            Long claimCategoryId
    );
}
