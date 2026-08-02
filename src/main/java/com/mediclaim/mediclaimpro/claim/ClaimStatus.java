package com.mediclaim.mediclaimpro.claim;

// Defines the allowed stages in the medical claim workflow.
public enum ClaimStatus {

    SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    REJECTED,
    PROCESSING,
    REIMBURSED
}
