package com.mediclaim.mediclaimpro.claim;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Defines the medical claim data returned by the API.
public class ClaimResponse {

    private Long id;
    private String claimNumber;

    private Long employeeId;
    private String employeeName;

    private Long claimCategoryId;
    private String claimCategoryName;

    private String providerName;
    private LocalDate treatmentDate;
    private BigDecimal claimedAmount;
    private String description;
    private ClaimStatus status;
    private LocalDateTime submittedAt;

    private String rejectionReason;
    private LocalDateTime reviewedAt;

    public ClaimResponse(
            Long id,
            String claimNumber,
            Long employeeId,
            String employeeName,
            Long claimCategoryId,
            String claimCategoryName,
            String providerName,
            LocalDate treatmentDate,
            BigDecimal claimedAmount,
            String description,
            ClaimStatus status,
            LocalDateTime submittedAt,
            String rejectionReason,
            LocalDateTime reviewedAt
    ) {
        this.id = id;
        this.claimNumber = claimNumber;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.claimCategoryId = claimCategoryId;
        this.claimCategoryName = claimCategoryName;
        this.providerName = providerName;
        this.treatmentDate = treatmentDate;
        this.claimedAmount = claimedAmount;
        this.description = description;
        this.status = status;
        this.submittedAt = submittedAt;
        this.rejectionReason = rejectionReason;
        this.reviewedAt = reviewedAt;
    }

    public Long getId() {
        return id;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Long getClaimCategoryId() {
        return claimCategoryId;
    }

    public String getClaimCategoryName() {
        return claimCategoryName;
    }

    public String getProviderName() {
        return providerName;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    public BigDecimal getClaimedAmount() {
        return claimedAmount;
    }

    public String getDescription() {
        return description;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
}