package com.mediclaim.mediclaimpro.claim;

import jakarta.validation.constraints.NotBlank;

// Defines the reason HR provides when rejecting a claim.
public class RejectClaimRequest {

    @NotBlank(message = "Rejection reason is required")
    private String rejectionReason;

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}