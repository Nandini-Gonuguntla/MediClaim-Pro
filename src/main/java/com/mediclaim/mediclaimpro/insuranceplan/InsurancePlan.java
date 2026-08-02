package com.mediclaim.mediclaimpro.insuranceplan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

// Represents an employee medical insurance plan stored in the database.
@Entity
@Table(name = "insurance_plans")
public class InsurancePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal annualLimit;

    public InsurancePlan() {
    }

    public InsurancePlan(
            String name,
            String description,
            BigDecimal annualLimit
    ) {
        this.name = name;
        this.description = description;
        this.annualLimit = annualLimit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAnnualLimit() {
        return annualLimit;
    }

    public void setAnnualLimit(BigDecimal annualLimit) {
        this.annualLimit = annualLimit;
    }
}