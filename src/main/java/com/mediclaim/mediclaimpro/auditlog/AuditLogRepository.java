package com.mediclaim.mediclaimpro.auditlog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Handles database operations for audit-log records.
public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByClaim_IdOrderByCreatedAtAsc(Long claimId);
}