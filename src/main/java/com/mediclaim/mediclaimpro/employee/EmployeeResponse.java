package com.mediclaim.mediclaimpro.employee;

// Defines the employee data returned by the API.
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long insurancePlanId;
    private String insurancePlanName;

    public EmployeeResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            Long insurancePlanId,
            String insurancePlanName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.insurancePlanId = insurancePlanId;
        this.insurancePlanName = insurancePlanName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getInsurancePlanId() {
        return insurancePlanId;
    }

    public String getInsurancePlanName() {
        return insurancePlanName;
    }
}