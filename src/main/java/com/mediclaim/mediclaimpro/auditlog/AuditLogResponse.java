package com.mediclaim.mediclaimpro.auditlog;

import com.mediclaim.mediclaimpro.claim.ClaimStatus;

import java.time.LocalDateTime;

// Controls the audit-log information returned through the API.
public class AuditLogResponse {

    private Long id;
    private Long claimId;
    private String claimNumber;
    private AuditAction action;
    private ClaimStatus previousStatus;
    private ClaimStatus newStatus;
    private String performedBy;
    private String message;
    private LocalDateTime createdAt;

    public AuditLogResponse(
            Long id,
            Long claimId,
            String claimNumber,
            AuditAction action,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            String performedBy,
            String message,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.claimId = claimId;
        this.claimNumber = claimNumber;
        this.action = action;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.performedBy = performedBy;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getClaimId() {
        return claimId;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public AuditAction getAction() {
        return action;
    }

    public ClaimStatus getPreviousStatus() {
        return previousStatus;
    }

    public ClaimStatus getNewStatus() {
        return newStatus;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}