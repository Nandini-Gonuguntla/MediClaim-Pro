# MediClaim Pro

MediClaim Pro is a Spring Boot application for managing employee medical reimbursement claims.

The system allows employees to submit claims, HR managers to approve or reject them, and finance administrators to process and reimburse approved claims.

It also tracks remaining insurance coverage and stores a complete audit history for every claim.

## Main Features

- Create and manage employees
- Create insurance plans
- Create claim categories
- Configure coverage limits for each plan and category
- Assign insurance plans to employees
- Submit medical claims
- Detect duplicate claims
- Validate remaining coverage
- Approve or reject claims
- Process approved claims
- Mark claims as reimbursed
- View employee claim history
- View remaining coverage balance
- Store complete audit logs
- Validate request data
- Return consistent API error responses
- Test and document APIs using Swagger

## Claim Workflow

```text
SUBMITTED
→ APPROVED
→ PROCESSING
→ REIMBURSED

A submitted claim can also be rejected:

SUBMITTED
→ REJECTED

User Roles
Employee
Submits medical claims
Views submitted claims
Checks remaining coverage
HR Manager
Reviews submitted claims
Approves valid claims
Rejects invalid claims with a reason
Finance Admin
Starts processing approved claims
Marks processed claims as reimbursed
System Admin
Creates insurance plans
Creates claim categories
Configures category coverage limits
Assigns plans to employees

Technologies Used
Java 17
Spring Boot
Spring MVC
Spring Data JPA
Hibernate
MySQL
Maven
Jakarta Bean Validation
Swagger / OpenAPI
Postman
IntelliJ IDEA
Main Entities

Employee:
Stores employee information and the assigned insurance plan.

InsurancePlan:
Represents a medical reimbursement plan.

ClaimCategory:
Represents categories such as:
Dental
Vision
Hospital
Pharmacy
Laboratory

PlanCoverage:
Connects an insurance plan with a claim category and stores the coverage limit.

Claim:Stores submitted claim details, including:
Claim number
Employee
Claim category
Provider name
Treatment date
Claimed amount
Description
Claim status
Submission time
Review time
Rejection reason

AuditLog:Stores the complete history of important claim actions.

Example:
CLAIM_SUBMITTED
CLAIM_APPROVED
CLAIM_PROCESSING_STARTED
CLAIM_REIMBURSED
Coverage Calculation

The remaining coverage is calculated using:
Coverage Limit
− Approved Claims
− Processing Claims
− Reimbursed Claims
= Remaining Balance

Submitted and rejected claims do not reduce the used coverage amount.

Important API Endpoints
Employees
POST /api/employees
GET /api/employees
GET /api/employees/{id}
Insurance Plans
POST /api/insurance-plans
GET /api/insurance-plans
Claim Categories
POST /api/claim-categories
GET /api/claim-categories
Plan Coverage
POST /api/plan-coverages

Claims
POST /api/claims
GET /api/claims/{claimId}
GET /api/claims/employee/{employeeId}
GET /api/claims/status/{status}
PUT /api/claims/{claimId}/approve
PUT /api/claims/{claimId}/reject
PUT /api/claims/{claimId}/process
PUT /api/claims/{claimId}/reimburse

Coverage Balance
GET /api/claims/employee/{employeeId}/category/{claimCategoryId}/balance

Audit History
GET /api/audit-logs/claim/{claimId}

Swagger Documentation
Run the Spring Boot application and open:

http://localhost:8080/swagger-ui/index.html

The generated OpenAPI JSON is available at:

http://localhost:8080/v3/api-docs
Example Claim Request
{
  "employeeId": 1,
  "claimCategoryId": 1,
  "providerName": "Austin Dental Center",
  "treatmentDate": "2026-08-01",
  "claimedAmount": 500,
  "description": "Dental treatment reimbursement"
}
Example Successful Response
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
Validation Rules

The application validates:

Employee ID is required
Claim category ID is required
Provider name is required
Treatment date cannot be in the future
Claimed amount must be greater than zero
Description is required
Duplicate claims are rejected
Claims cannot exceed remaining coverage
Only submitted claims can be approved or rejected
Only approved claims can be processed
Only processing claims can be reimbursed
HTTP Status Codes
200 OK
→ Request completed successfully

201 Created
→ New resource created successfully

400 Bad Request
→ Request validation failed

409 Conflict
→ Business rule was violated

500 Internal Server Error
→ Unexpected application error
Database

The project uses MySQL.

Main tables include:

employees
insurance_plans
claim_categories
plan_coverages
claims
audit_logs
Running the Application
Create a MySQL database.
Update database settings in application.properties.
Reload Maven dependencies.
Run MediclaimProApplication.
Open Swagger UI.
Test the APIs.
Project Purpose

This project demonstrates:

REST API development
Layered Spring Boot architecture
Database relationships
Business-rule validation
Workflow status management
Audit logging
Global exception handling
Request validation
Swagger API documentation