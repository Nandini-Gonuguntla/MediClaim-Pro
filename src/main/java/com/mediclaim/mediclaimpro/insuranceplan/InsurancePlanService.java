package com.mediclaim.mediclaimpro.insuranceplan;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// Contains business rules for creating and retrieving insurance plans.
@Service
public class InsurancePlanService {

    private final InsurancePlanRepository insurancePlanRepository;

    public InsurancePlanService(
            InsurancePlanRepository insurancePlanRepository
    ) {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    // Prevents duplicate plans and rejects invalid annual coverage limits.
    public InsurancePlan createPlan(InsurancePlan plan) {
        if (insurancePlanRepository.existsByNameIgnoreCase(plan.getName())) {
            throw new IllegalArgumentException(
                    "Insurance plan already exists: " + plan.getName()
            );
        }

        if (plan.getAnnualLimit() == null
                || plan.getAnnualLimit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Annual limit must be greater than zero"
            );
        }

        return insurancePlanRepository.save(plan);
    }

    public List<InsurancePlan> getAllPlans() {
        return insurancePlanRepository.findAll();
    }

    public InsurancePlan getPlanById(Long id) {
        return insurancePlanRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Insurance plan not found with id: " + id
                        )
                );
    }
}