package com.mediclaim.mediclaimpro.plancoverage;

import com.mediclaim.mediclaimpro.claimcategory.ClaimCategory;
import com.mediclaim.mediclaimpro.insuranceplan.InsurancePlan;
import jakarta.persistence.*;

import java.math.BigDecimal;

// Stores the coverage limit assigned to one category under an insurance plan.
@Entity
@Table(
        name = "plan_coverages",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"insurance_plan_id", "claim_category_id"}
                )
        }
)
public class PlanCoverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insurance_plan_id")
    private InsurancePlan insurancePlan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "claim_category_id")
    private ClaimCategory claimCategory;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal coverageLimit;

    public PlanCoverage() {
    }

    public PlanCoverage(
            InsurancePlan insurancePlan,
            ClaimCategory claimCategory,
            BigDecimal coverageLimit
    ) {
        this.insurancePlan = insurancePlan;
        this.claimCategory = claimCategory;
        this.coverageLimit = coverageLimit;
    }

    public Long getId() {
        return id;
    }

    public InsurancePlan getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(InsurancePlan insurancePlan) {
        this.insurancePlan = insurancePlan;
    }

    public ClaimCategory getClaimCategory() {
        return claimCategory;
    }

    public void setClaimCategory(ClaimCategory claimCategory) {
        this.claimCategory = claimCategory;
    }

    public BigDecimal getCoverageLimit() {
        return coverageLimit;
    }

    public void setCoverageLimit(BigDecimal coverageLimit) {
        this.coverageLimit = coverageLimit;
    }
}