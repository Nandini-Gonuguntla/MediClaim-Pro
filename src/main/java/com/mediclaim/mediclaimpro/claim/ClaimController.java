package com.mediclaim.mediclaimpro.claim;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Exposes REST endpoints for submitting and retrieving medical claims.
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Submits a new medical reimbursement claim.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimResponse submitClaim(
            @Valid @RequestBody ClaimRequest request
    ) {
        return claimService.submitClaim(request);
    }

    @GetMapping("/{id}")
    public ClaimResponse getClaimById(
            @PathVariable Long id
    ) {
        return claimService.getClaimById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ClaimResponse> getClaimsByEmployee(
            @PathVariable Long employeeId
    ) {
        return claimService.getClaimsByEmployee(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<ClaimResponse> getClaimsByStatus(
            @PathVariable ClaimStatus status
    ) {
        return claimService.getClaimsByStatus(status);
    }
    // HR approves a submitted claim.
    @PutMapping("/{claimId}/approve")
    public ClaimResponse approveClaim(
            @PathVariable Long claimId
    ) {
        return claimService.approveClaim(claimId);
    }

    // HR rejects a submitted claim and provides a reason.
    @PutMapping("/{claimId}/reject")
    public ClaimResponse rejectClaim(
            @PathVariable Long claimId,
            @Valid @RequestBody RejectClaimRequest request
    ) {
        return claimService.rejectClaim(
                claimId,
                request.getRejectionReason()
        );

    }
    // Finance starts processing an approved claim.
    @PutMapping("/{claimId}/process")
    public ClaimResponse processClaim(
            @PathVariable Long claimId
    ) {
        return claimService.processClaim(claimId);
    }

    // Finance marks a processing claim as reimbursed.
    @PutMapping("/{claimId}/reimburse")
    public ClaimResponse reimburseClaim(
            @PathVariable Long claimId
    ) {
        return claimService.reimburseClaim(claimId);
    }

    // Returns used and remaining coverage for one employee and category.
    @GetMapping("/employee/{employeeId}/category/{claimCategoryId}/balance")
    public CoverageBalanceResponse getCoverageBalance(
            @PathVariable Long employeeId,
            @PathVariable Long claimCategoryId
    ) {
        return claimService.getCoverageBalance(
                employeeId,
                claimCategoryId
        );
    }
}
