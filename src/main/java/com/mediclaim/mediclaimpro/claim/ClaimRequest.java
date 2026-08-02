package com.mediclaim.mediclaimpro.claim;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Claim category ID is required")
    private Long claimCategoryId;

    @NotBlank(message = "Provider name is required")
    @Size(max = 150, message = "Provider name cannot exceed 150 characters")
    private String providerName;

    @NotNull(message = "Treatment date is required")
    @PastOrPresent(message = "Treatment date cannot be in the future")
    private LocalDate treatmentDate;

    @NotNull(message = "Claimed amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Claimed amount must be greater than zero"
    )
    private BigDecimal claimedAmount;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    public ClaimRequest() {
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getClaimCategoryId() {
        return claimCategoryId;
    }

    public void setClaimCategoryId(Long claimCategoryId) {
        this.claimCategoryId = claimCategoryId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(LocalDate treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public BigDecimal getClaimedAmount() {
        return claimedAmount;
    }

    public void setClaimedAmount(BigDecimal claimedAmount) {
        this.claimedAmount = claimedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}