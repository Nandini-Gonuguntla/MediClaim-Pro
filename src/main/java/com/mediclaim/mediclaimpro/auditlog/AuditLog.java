package com.mediclaim.mediclaimpro.auditlog;

import com.mediclaim.mediclaimpro.claim.Claim;
import com.mediclaim.mediclaimpro.claim.ClaimStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

// Stores the permanent history of important claim actions.
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many audit records can belong to one claim.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private ClaimStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ClaimStatus newStatus;

    @Column(name = "performed_by", nullable = false)
    private String performedBy;

    @Column(length = 1000)
    private String message;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AuditLog() {
    }

    public AuditLog(
            Claim claim,
            AuditAction action,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            String performedBy,
            String message
    ) {
        this.claim = claim;
        this.action = action;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.performedBy = performedBy;
        this.message = message;
    }

    // Automatically stores the time before a new audit record is inserted.
    @PrePersist
    public void setCreatedAtAutomatically() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public ClaimStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(ClaimStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public ClaimStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ClaimStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}