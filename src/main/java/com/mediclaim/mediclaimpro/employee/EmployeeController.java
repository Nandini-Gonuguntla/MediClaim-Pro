package com.mediclaim.mediclaimpro.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Exposes REST endpoints for employee operations.
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Creates a new employee after validating the request body.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(
            @Valid @RequestBody EmployeeRequest request
    ) {
        return employeeService.createEmployee(request);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    // Assigns an insurance plan to an existing employee.
    @PutMapping("/{employeeId}/insurance-plan/{insurancePlanId}")
    public EmployeeResponse assignInsurancePlan(
            @PathVariable Long employeeId,
            @PathVariable Long insurancePlanId
    ) {
        return employeeService.assignInsurancePlan(
                employeeId,
                insurancePlanId
        );
    }
}