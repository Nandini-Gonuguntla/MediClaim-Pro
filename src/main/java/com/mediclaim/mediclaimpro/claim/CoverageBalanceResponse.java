package com.mediclaim.mediclaimpro.claim;

import java.math.BigDecimal;

// Shows how much category coverage an employee has used and still has available.
public class CoverageBalanceResponse {

    private Long employeeId;
    private Long claimCategoryId;
    private String claimCategoryName;
    private BigDecimal coverageLimit;
    private BigDecimal usedAmount;
    private BigDecimal remainingBalance;

    public CoverageBalanceResponse(
            Long employeeId,
            Long claimCategoryId,
            String claimCategoryName,
            BigDecimal coverageLimit,
            BigDecimal usedAmount,
            BigDecimal remainingBalance
    ) {
        this.employeeId = employeeId;
        this.claimCategoryId = claimCategoryId;
        this.claimCategoryName = claimCategoryName;
        this.coverageLimit = coverageLimit;
        this.usedAmount = usedAmount;
        this.remainingBalance = remainingBalance;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getClaimCategoryId() {
        return claimCategoryId;
    }

    public String getClaimCategoryName() {
        return claimCategoryName;
    }

    public BigDecimal getCoverageLimit() {
        return coverageLimit;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }
}