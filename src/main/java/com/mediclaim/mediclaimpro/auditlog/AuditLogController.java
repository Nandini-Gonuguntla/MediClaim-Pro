package com.mediclaim.mediclaimpro.auditlog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Provides API endpoints for viewing claim audit history.
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Returns the complete audit history of one claim.
    @GetMapping("/claim/{claimId}")
    public ResponseEntity<List<AuditLogResponse>> getClaimHistory(
            @PathVariable Long claimId
    ) {
        List<AuditLogResponse> history =
                auditLogService.getClaimHistory(claimId);

        return ResponseEntity.ok(history);
    }
}