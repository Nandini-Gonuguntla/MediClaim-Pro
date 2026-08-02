package com.mediclaim.mediclaimpro.plancoverage;

import com.mediclaim.mediclaimpro.claimcategory.ClaimCategory;
import com.mediclaim.mediclaimpro.claimcategory.ClaimCategoryRepository;
import com.mediclaim.mediclaimpro.insuranceplan.InsurancePlan;
import com.mediclaim.mediclaimpro.insuranceplan.InsurancePlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// Contains business rules for assigning category limits to insurance plans.
@Service
public class PlanCoverageService {

    private final PlanCoverageRepository planCoverageRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final ClaimCategoryRepository claimCategoryRepository;

    public PlanCoverageService(
            PlanCoverageRepository planCoverageRepository,
            InsurancePlanRepository insurancePlanRepository,
            ClaimCategoryRepository claimCategoryRepository
    ) {
        this.planCoverageRepository = planCoverageRepository;
        this.insurancePlanRepository = insurancePlanRepository;
        this.claimCategoryRepository = claimCategoryRepository;
    }

    // Creates one category-specific coverage rule for an insurance plan.
    public PlanCoverage createCoverage(
            Long insurancePlanId,
            Long claimCategoryId,
            BigDecimal coverageLimit
    ) {
        if (coverageLimit == null
                || coverageLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Coverage limit must be greater than zero"
            );
        }

        if (planCoverageRepository
                .existsByInsurancePlanIdAndClaimCategoryId(
                        insurancePlanId,
                        claimCategoryId
                )) {
            throw new IllegalArgumentException(
                    "Coverage already exists for this plan and category"
            );
        }

        InsurancePlan insurancePlan =
                insurancePlanRepository.findById(insurancePlanId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Insurance plan not found with id: "
                                                + insurancePlanId
                                )
                        );

        ClaimCategory claimCategory =
                claimCategoryRepository.findById(claimCategoryId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Claim category not found with id: "
                                                + claimCategoryId
                                )
                        );

        PlanCoverage planCoverage = new PlanCoverage(
                insurancePlan,
                claimCategory,
                coverageLimit
        );

        return planCoverageRepository.save(planCoverage);
    }

    public List<PlanCoverage> getCoverageByPlan(Long insurancePlanId) {
        return planCoverageRepository
                .findByInsurancePlanId(insurancePlanId);
    }
}