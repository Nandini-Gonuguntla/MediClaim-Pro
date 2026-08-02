package com.mediclaim.mediclaimpro.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Handles database operations for the Employee entity.
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    // Finds an employee using the unique email address.
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}