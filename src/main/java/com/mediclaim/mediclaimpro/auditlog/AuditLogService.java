package com.mediclaim.mediclaimpro.auditlog;

import com.mediclaim.mediclaimpro.claim.Claim;
import com.mediclaim.mediclaimpro.claim.ClaimStatus;
import org.springframework.stereotype.Service;

import java.util.List;

// Handles saving and reading claim audit history.
@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Saves one audit event in the database.
    public void saveAuditLog(
            Claim claim,
            AuditAction action,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            String performedBy,
            String message
    ) {
        AuditLog auditLog = new AuditLog(
                claim,
                action,
                previousStatus,
                newStatus,
                performedBy,
                message
        );

        auditLogRepository.save(auditLog);
    }

    // Returns all audit records for one claim, oldest first.
    public List<AuditLogResponse> getClaimHistory(Long claimId) {

        return auditLogRepository
                .findByClaim_IdOrderByCreatedAtAsc(claimId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private AuditLogResponse convertToResponse(AuditLog auditLog) {

        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getClaim().getId(),
                auditLog.getClaim().getClaimNumber(),
                auditLog.getAction(),
                auditLog.getPreviousStatus(),
                auditLog.getNewStatus(),
                auditLog.getPerformedBy(),
                auditLog.getMessage(),
                auditLog.getCreatedAt()
        );
    }
}