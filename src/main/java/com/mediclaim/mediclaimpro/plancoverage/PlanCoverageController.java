package com.mediclaim.mediclaimpro.plancoverage;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Exposes REST endpoints for category-wise insurance coverage limits.
@RestController
@RequestMapping("/api/plan-coverages")
public class PlanCoverageController {

    private final PlanCoverageService planCoverageService;

    public PlanCoverageController(
            PlanCoverageService planCoverageService
    ) {
        this.planCoverageService = planCoverageService;
    }

    // Creates one coverage limit for a plan and category combination.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanCoverage createCoverage(
            @Valid @RequestBody PlanCoverageRequest request
    ) {
        return planCoverageService.createCoverage(
                request.getInsurancePlanId(),
                request.getClaimCategoryId(),
                request.getCoverageLimit()
        );
    }

    @GetMapping("/plan/{insurancePlanId}")
    public List<PlanCoverage> getCoverageByPlan(
            @PathVariable Long insurancePlanId
    ) {
        return planCoverageService.getCoverageByPlan(insurancePlanId);
    }
}