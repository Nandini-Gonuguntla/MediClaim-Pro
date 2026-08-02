package com.mediclaim.mediclaimpro.insuranceplan;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Exposes REST endpoints for insurance plan operations.
@RestController
@RequestMapping("/api/insurance-plans")
public class InsurancePlanController {

    private final InsurancePlanService insurancePlanService;

    public InsurancePlanController(
            InsurancePlanService insurancePlanService
    ) {
        this.insurancePlanService = insurancePlanService;
    }

    // Creates a new insurance plan.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsurancePlan createPlan(
            @RequestBody InsurancePlan plan
    ) {
        return insurancePlanService.createPlan(plan);
    }

    @GetMapping
    public List<InsurancePlan> getAllPlans() {
        return insurancePlanService.getAllPlans();
    }

    @GetMapping("/{id}")
    public InsurancePlan getPlanById(
            @PathVariable Long id
    ) {
        return insurancePlanService.getPlanById(id);
    }
}