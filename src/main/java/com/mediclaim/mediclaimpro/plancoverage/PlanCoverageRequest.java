package com.mediclaim.mediclaimpro.plancoverage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// Defines the data required to create a category-specific coverage limit.
public class PlanCoverageRequest {

    @NotNull(message = "Insurance plan ID is required")
    private Long insurancePlanId;

    @NotNull(message = "Claim category ID is required")
    private Long claimCategoryId;

    @NotNull(message = "Coverage limit is required")
    @Positive(message = "Coverage limit must be greater than zero")
    private BigDecimal coverageLimit;

    public Long getInsurancePlanId() {
        return insurancePlanId;
    }

    public void setInsurancePlanId(Long insurancePlanId) {
        this.insurancePlanId = insurancePlanId;
    }

    public Long getClaimCategoryId() {
        return claimCategoryId;
    }

    public void setClaimCategoryId(Long claimCategoryId) {
        this.claimCategoryId = claimCategoryId;
    }

    public BigDecimal getCoverageLimit() {
        return coverageLimit;
    }

    public void setCoverageLimit(BigDecimal coverageLimit) {
        this.coverageLimit = coverageLimit;
    }
}