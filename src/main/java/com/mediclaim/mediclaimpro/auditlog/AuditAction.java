package com.mediclaim.mediclaimpro.auditlog;

// Represents important actions performed during the claim workflow.
public enum AuditAction {

    CLAIM_SUBMITTED,
    CLAIM_APPROVED,
    CLAIM_REJECTED,
    CLAIM_PROCESSING_STARTED,
    CLAIM_REIMBURSED
}
