# MediClaim Pro

MediClaim Pro is a Spring Boot REST API for managing employee medical reimbursement claims. It supports the full claim lifecycle from employee submission to HR review, finance processing, reimbursement, coverage tracking, and audit history.

The project is designed as a layered backend application with controllers, services, repositories, validation, exception handling, MySQL persistence, and Swagger/OpenAPI documentation.

## Project Highlights

- Employee medical claim submission and review workflow
- Insurance plan and claim category management
- Category-wise coverage limits for each insurance plan
- Remaining coverage balance calculation
- Duplicate claim and business-rule validation
- Claim approval, rejection, processing, and reimbursement flow
- Complete audit history for important claim actions
- Consistent API error responses through global exception handling
- Swagger UI and OpenAPI JSON for API testing and documentation

## Claim Workflow

```text
SUBMITTED -> APPROVED -> PROCESSING -> REIMBURSED
SUBMITTED -> REJECTED
```

Only valid state transitions are allowed. For example, only submitted claims can be approved or rejected, only approved claims can move to processing, and only processing claims can be reimbursed.

## User Responsibilities

| Role | Responsibilities |
| --- | --- |
| Employee | Submit medical claims, view claim history, and check remaining coverage |
| HR Manager | Review submitted claims, approve valid claims, and reject invalid claims with a reason |
| Finance Admin | Start processing approved claims and mark processed claims as reimbursed |
| System Admin | Create insurance plans, claim categories, coverage limits, and assign plans to employees |

## Tech Stack

| Area | Technologies |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 4, Spring MVC |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Validation | Jakarta Bean Validation |
| API Docs | Swagger UI, OpenAPI, Springdoc |
| Build Tool | Maven |
| Developer Tools | IntelliJ IDEA, Postman |

## Architecture

```text
Controller Layer
    -> Handles REST requests and response status codes

Service Layer
    -> Applies business rules, workflow transitions, and coverage logic

Repository Layer
    -> Performs database operations through Spring Data JPA

Database Layer
    -> Stores employees, plans, categories, claims, coverage, and audit logs
```

## Main Domain Entities

| Entity | Purpose |
| --- | --- |
| Employee | Stores employee details and assigned insurance plan |
| InsurancePlan | Represents a medical reimbursement plan |
| ClaimCategory | Stores categories such as Dental, Vision, Hospital, Pharmacy, and Laboratory |
| PlanCoverage | Connects an insurance plan with a claim category and coverage limit |
| Claim | Stores submitted claim details, status, amount, provider, dates, and review information |
| AuditLog | Stores the history of claim actions such as submission, approval, processing, and reimbursement |

## Coverage Calculation

Remaining coverage is calculated from the configured category coverage limit.

```text
Remaining Balance = Coverage Limit - Approved Claims - Processing Claims - Reimbursed Claims
```

Submitted and rejected claims do not reduce the used coverage amount.

## API Endpoints

### Employees

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/employees` | Create a new employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| PUT | `/api/employees/{employeeId}/insurance-plan/{insurancePlanId}` | Assign an insurance plan to an employee |

### Insurance Plans

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/insurance-plans` | Create an insurance plan |
| GET | `/api/insurance-plans` | Get all insurance plans |
| GET | `/api/insurance-plans/{id}` | Get insurance plan by ID |

### Claim Categories

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/claim-categories` | Create a claim category |
| GET | `/api/claim-categories` | Get all claim categories |
| GET | `/api/claim-categories/{id}` | Get claim category by ID |

### Plan Coverage

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/plan-coverages` | Create a coverage limit for a plan and category |
| GET | `/api/plan-coverages/plan/{insurancePlanId}` | Get coverage limits by insurance plan |

### Claims

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/claims` | Submit a medical claim |
| GET | `/api/claims/{id}` | Get claim by ID |
| GET | `/api/claims/employee/{employeeId}` | Get claims by employee |
| GET | `/api/claims/status/{status}` | Get claims by status |
| PUT | `/api/claims/{claimId}/approve` | Approve a submitted claim |
| PUT | `/api/claims/{claimId}/reject` | Reject a submitted claim with a reason |
| PUT | `/api/claims/{claimId}/process` | Start processing an approved claim |
| PUT | `/api/claims/{claimId}/reimburse` | Mark a processing claim as reimbursed |
| GET | `/api/claims/employee/{employeeId}/category/{claimCategoryId}/balance` | Get remaining coverage balance |

### Audit History

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/audit-logs/claim/{claimId}` | Get complete audit history for a claim |

## Swagger Documentation

After running the application, open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Example Claim Request

```json
{
  "employeeId": 1,
  "claimCategoryId": 1,
  "providerName": "Austin Dental Center",
  "treatmentDate": "2026-08-01",
  "claimedAmount": 500,
  "description": "Dental treatment reimbursement"
}
```

## Example Successful Response

```json
{
  "id": 1,
  "claimNumber": "CLM-1785612143913",
  "employeeId": 1,
  "employeeName": "Ravi Kumar",
  "claimCategoryId": 1,
  "claimCategoryName": "Dental",
  "providerName": "Austin Dental Center",
  "treatmentDate": "2026-08-01",
  "claimedAmount": 500,
  "description": "Dental treatment reimbursement",
  "status": "SUBMITTED"
}
```

## Validation and Business Rules

- Employee ID is required.
- Claim category ID is required.
- Provider name is required.
- Treatment date cannot be in the future.
- Claimed amount must be greater than zero.
- Description is required.
- Duplicate claims are rejected.
- Claims cannot exceed the remaining coverage balance.
- Only submitted claims can be approved or rejected.
- Only approved claims can be moved to processing.
- Only processing claims can be reimbursed.

## Error Handling

The API uses a global exception handler to return consistent responses.

| Status | Meaning |
| --- | --- |
| `200 OK` | Request completed successfully |
| `201 Created` | New resource created successfully |
| `400 Bad Request` | Request validation failed |
| `409 Conflict` | Business rule was violated |
| `500 Internal Server Error` | Unexpected application error |

## Database

The project uses MySQL.

Main tables:

- `employees`
- `insurance_plans`
- `claim_categories`
- `plan_coverages`
- `claims`
- `audit_logs`

## Running the Application

1. Create a MySQL database named `mediclaim_db`.
2. Set database credentials as environment variables:

```text
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

4. Open Swagger UI and test the APIs:

```text
http://localhost:8080/swagger-ui/index.html
```

## Project Purpose

This project demonstrates practical backend development with Spring Boot, REST APIs, layered architecture, relational database design, workflow state management, validation, global exception handling, audit logging, and API documentation.
