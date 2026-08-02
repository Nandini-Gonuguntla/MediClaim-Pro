package com.mediclaim.mediclaimpro.claim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Handles database operations for employee medical claims.
public interface ClaimRepository
        extends JpaRepository<Claim, Long> {

    List<Claim> findByEmployeeId(Long employeeId);

    List<Claim> findByStatus(ClaimStatus status);

    boolean existsByEmployeeIdAndTreatmentDateAndProviderNameIgnoreCaseAndClaimedAmount(
            Long employeeId,
            LocalDate treatmentDate,
            String providerName,
            BigDecimal claimedAmount
    );

    // Calculates the amount already used by one employee in one category.
    @Query("""
            SELECT COALESCE(SUM(c.claimedAmount), 0)
            FROM Claim c
            WHERE c.employee.id = :employeeId
              AND c.claimCategory.id = :categoryId
              AND c.status IN :statuses
            """)
    BigDecimal calculateUsedAmount(
            @Param("employeeId") Long employeeId,
            @Param("categoryId") Long categoryId,
            @Param("statuses") List<ClaimStatus> statuses
    );
}