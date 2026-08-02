package com.mediclaim.mediclaimpro.employee;

import com.mediclaim.mediclaimpro.insuranceplan.InsurancePlan;
import com.mediclaim.mediclaimpro.insuranceplan.InsurancePlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Contains business logic for employee creation, retrieval, and plan assignment.
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final InsurancePlanRepository insurancePlanRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            InsurancePlanRepository insurancePlanRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.insurancePlanRepository = insurancePlanRepository;
    }

    // Prevents duplicate employee registration using the email address.
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Employee already exists with email: " + request.getEmail()
            );
        }

        Employee employee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponse(savedEmployee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with id: " + id
                        )
                );

        return convertToResponse(employee);
    }

    // Assigns an existing insurance plan to an existing employee.
    public EmployeeResponse assignInsurancePlan(
            Long employeeId,
            Long insurancePlanId
    ) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found with id: " + employeeId
                        )
                );

        InsurancePlan insurancePlan =
                insurancePlanRepository.findById(insurancePlanId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Insurance plan not found with id: "
                                                + insurancePlanId
                                )
                        );

        employee.setInsurancePlan(insurancePlan);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponse(savedEmployee);
    }

    // Converts the entity into the controlled response returned by the API.
    private EmployeeResponse convertToResponse(Employee employee) {

        Long insurancePlanId = null;
        String insurancePlanName = null;

        if (employee.getInsurancePlan() != null) {
            insurancePlanId = employee.getInsurancePlan().getId();
            insurancePlanName = employee.getInsurancePlan().getName();
        }

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                insurancePlanId,
                insurancePlanName
        );
    }
}