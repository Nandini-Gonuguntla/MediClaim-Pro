package com.mediclaim.mediclaimpro.claim;

import com.mediclaim.mediclaimpro.auditlog.AuditAction;
import com.mediclaim.mediclaimpro.auditlog.AuditLogService;
import com.mediclaim.mediclaimpro.claimcategory.ClaimCategory;
import com.mediclaim.mediclaimpro.claimcategory.ClaimCategoryRepository;
import com.mediclaim.mediclaimpro.employee.Employee;
import com.mediclaim.mediclaimpro.employee.EmployeeRepository;
import com.mediclaim.mediclaimpro.plancoverage.PlanCoverage;
import com.mediclaim.mediclaimpro.plancoverage.PlanCoverageRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Contains business rules for submitting, reviewing, and processing claims.
@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final EmployeeRepository employeeRepository;
    private final ClaimCategoryRepository claimCategoryRepository;
    private final PlanCoverageRepository planCoverageRepository;
    private final AuditLogService auditLogService;

    public ClaimService(
            ClaimRepository claimRepository,
            EmployeeRepository employeeRepository,
            ClaimCategoryRepository claimCategoryRepository,
            PlanCoverageRepository planCoverageRepository,
            AuditLogService auditLogService
    ) {
        this.claimRepository = claimRepository;
        this.employeeRepository = employeeRepository;
        this.claimCategoryRepository = claimCategoryRepository;
        this.planCoverageRepository = planCoverageRepository;
        this.auditLogService = auditLogService;
    }

    // Employee submits a new medical claim.
    public ClaimResponse submitClaim(ClaimRequest request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with id: "
                                        + request.getEmployeeId()
                        )
                );

        if (employee.getInsurancePlan() == null) {
            throw new IllegalArgumentException(
                    "Employee does not have an insurance plan"
            );
        }

        ClaimCategory claimCategory = claimCategoryRepository
                .findById(request.getClaimCategoryId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim category not found with id: "
                                        + request.getClaimCategoryId()
                        )
                );

        boolean duplicateExists = claimRepository
                .existsByEmployeeIdAndTreatmentDateAndProviderNameIgnoreCaseAndClaimedAmount(
                        request.getEmployeeId(),
                        request.getTreatmentDate(),
                        request.getProviderName(),
                        request.getClaimedAmount()
                );

        if (duplicateExists) {
            throw new IllegalArgumentException(
                    "Possible duplicate claim already exists"
            );
        }

        PlanCoverage planCoverage = planCoverageRepository
                .findByInsurancePlanIdAndClaimCategoryId(
                        employee.getInsurancePlan().getId(),
                        request.getClaimCategoryId()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No coverage configured for this plan and category"
                        )
                );

        List<ClaimStatus> coverageStatuses = List.of(
                ClaimStatus.APPROVED,
                ClaimStatus.PROCESSING,
                ClaimStatus.REIMBURSED
        );

        BigDecimal usedAmount = claimRepository.calculateUsedAmount(
                request.getEmployeeId(),
                request.getClaimCategoryId(),
                coverageStatuses
        );

        BigDecimal remainingBalance =
                planCoverage.getCoverageLimit().subtract(usedAmount);

        if (request.getClaimedAmount().compareTo(remainingBalance) > 0) {
            throw new IllegalArgumentException(
                    "Claimed amount exceeds remaining coverage. "
                            + "Remaining balance: " + remainingBalance
            );
        }

        Claim claim = new Claim();

        claim.setClaimNumber(generateClaimNumber());
        claim.setEmployee(employee);
        claim.setClaimCategory(claimCategory);
        claim.setProviderName(request.getProviderName());
        claim.setTreatmentDate(request.getTreatmentDate());
        claim.setClaimedAmount(request.getClaimedAmount());
        claim.setDescription(request.getDescription());
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setSubmittedAt(LocalDateTime.now());

        Claim savedClaim = claimRepository.save(claim);

        // Records the first event in the claim history.
        auditLogService.saveAuditLog(
                savedClaim,
                AuditAction.CLAIM_SUBMITTED,
                null,
                ClaimStatus.SUBMITTED,
                "EMPLOYEE",
                "Claim submitted successfully"
        );

        return convertToResponse(savedClaim);
    }

    public List<ClaimResponse> getClaimsByEmployee(Long employeeId) {
        return claimRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ClaimResponse> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ClaimResponse getClaimById(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim not found with id: " + claimId
                        )
                );

        return convertToResponse(claim);
    }

    // HR approves a submitted claim.
    public ClaimResponse approveClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim not found with id: " + claimId
                        )
                );

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new IllegalArgumentException(
                    "Only submitted claims can be approved"
            );
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.APPROVED);
        claim.setReviewedAt(LocalDateTime.now());

        Claim savedClaim = claimRepository.save(claim);

        auditLogService.saveAuditLog(
                savedClaim,
                AuditAction.CLAIM_APPROVED,
                previousStatus,
                ClaimStatus.APPROVED,
                "HR_MANAGER",
                "Claim approved by HR manager"
        );

        return convertToResponse(savedClaim);
    }

    // HR rejects a submitted claim.
    public ClaimResponse rejectClaim(
            Long claimId,
            String rejectionReason
    ) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim not found with id: " + claimId
                        )
                );

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new IllegalArgumentException(
                    "Only submitted claims can be rejected"
            );
        }

        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new IllegalArgumentException(
                    "Rejection reason is required"
            );
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.REJECTED);
        claim.setRejectionReason(rejectionReason);
        claim.setReviewedAt(LocalDateTime.now());

        Claim savedClaim = claimRepository.save(claim);

        auditLogService.saveAuditLog(
                savedClaim,
                AuditAction.CLAIM_REJECTED,
                previousStatus,
                ClaimStatus.REJECTED,
                "HR_MANAGER",
                "Claim rejected. Reason: " + rejectionReason
        );

        return convertToResponse(savedClaim);
    }

    // Finance starts processing an approved claim.
    public ClaimResponse processClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim not found with id: " + claimId
                        )
                );

        if (claim.getStatus() != ClaimStatus.APPROVED) {
            throw new IllegalArgumentException(
                    "Only approved claims can be processed"
            );
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.PROCESSING);

        Claim savedClaim = claimRepository.save(claim);

        auditLogService.saveAuditLog(
                savedClaim,
                AuditAction.CLAIM_PROCESSING_STARTED,
                previousStatus,
                ClaimStatus.PROCESSING,
                "FINANCE_ADMIN",
                "Finance started processing the claim"
        );

        return convertToResponse(savedClaim);
    }

    // Finance marks a processing claim as reimbursed.
    public ClaimResponse reimburseClaim(Long claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Claim not found with id: " + claimId
                        )
                );

        if (claim.getStatus() != ClaimStatus.PROCESSING) {
            throw new IllegalArgumentException(
                    "Only processing claims can be reimbursed"
            );
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.REIMBURSED);

        Claim savedClaim = claimRepository.save(claim);

        auditLogService.saveAuditLog(
                savedClaim,
                AuditAction.CLAIM_REIMBURSED,
                previousStatus,
                ClaimStatus.REIMBURSED,
                "FINANCE_ADMIN",
                "Claim reimbursement completed"
        );

        return convertToResponse(savedClaim);
    }

    // Calculates used and remaining coverage for one category.
    public CoverageBalanceResponse getCoverageBalance(
            Long employeeId,
            Long claimCategoryId
    ) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with id: " + employeeId
                        )
                );

        if (employee.getInsurancePlan() == null) {
            throw new IllegalArgumentException(
                    "Employee does not have an insurance plan"
            );
        }

        ClaimCategory claimCategory =
                claimCategoryRepository.findById(claimCategoryId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Claim category not found with id: "
                                                + claimCategoryId
                                )
                        );

        PlanCoverage planCoverage = planCoverageRepository
                .findByInsurancePlanIdAndClaimCategoryId(
                        employee.getInsurancePlan().getId(),
                        claimCategoryId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No coverage configured for this plan and category"
                        )
                );

        List<ClaimStatus> coverageStatuses = List.of(
                ClaimStatus.APPROVED,
                ClaimStatus.PROCESSING,
                ClaimStatus.REIMBURSED
        );

        BigDecimal usedAmount = claimRepository.calculateUsedAmount(
                employeeId,
                claimCategoryId,
                coverageStatuses
        );

        BigDecimal remainingBalance =
                planCoverage.getCoverageLimit().subtract(usedAmount);

        return new CoverageBalanceResponse(
                employeeId,
                claimCategoryId,
                claimCategory.getName(),
                planCoverage.getCoverageLimit(),
                usedAmount,
                remainingBalance
        );
    }

    private String generateClaimNumber() {
        return "CLM-" + System.currentTimeMillis();
    }

    // Converts a Claim entity into the response returned to Postman.
    private ClaimResponse convertToResponse(Claim claim) {

        String employeeName =
                claim.getEmployee().getFirstName()
                        + " "
                        + claim.getEmployee().getLastName();

        return new ClaimResponse(
                claim.getId(),
                claim.getClaimNumber(),
                claim.getEmployee().getId(),
                employeeName,
                claim.getClaimCategory().getId(),
                claim.getClaimCategory().getName(),
                claim.getProviderName(),
                claim.getTreatmentDate(),
                claim.getClaimedAmount(),
                claim.getDescription(),
                claim.getStatus(),
                claim.getSubmittedAt(),
                claim.getRejectionReason(),
                claim.getReviewedAt()
        );
    }
}