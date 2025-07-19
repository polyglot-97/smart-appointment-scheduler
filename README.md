<h1 align="center">Smart Appointment Scheduler</h1>

<p align="center">
  <b>Intelligent Rules Engine For Healthcare Appointment Scheduling</b><br/>
  <a href="https://github.com/your-repo-url"><img src="https://img.shields.io/badge/build-passing-brightgreen"/></a>
  <a href="https://adoptopenjdk.net/"><img src="https://img.shields.io/badge/Java-21%2B-blue"/></a>
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen"/></a>
  <a href="https://www.postgresql.org/"><img src="https://img.shields.io/badge/PostgreSQL-12%2B-blue"/></a>
</p>

---

## 📑 Table of Contents
- [Overview](#-overview)
- [US Healthcare Context](#-us-healthcare-context)
- [Features](#-features)
- [Quickstart](#-quickstart)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Rules Engine Explained](#-rules-engine-explained)
- [API Overview](#-api-overview)
- [Testing](#-testing)
- [Contributing](#-contributing)

---

## 🚀 Overview
Smart Appointment Scheduler is a robust backend system designed specifically for US healthcare organizations. It automates and optimizes appointment scheduling while supporting industry-standard workflows involving ICD codes, CPT codes, and modifiers. The system leverages a powerful rules engine to enforce complex business logic, prevent scheduling conflicts, and ensure compliance with US healthcare scheduling standards. Multiple user roles, provider availability management, and detailed patient encounter tracking are all accessible via a clean RESTful API.

---

## 🏥 US Healthcare Context

In the United States, healthcare appointment scheduling is closely tied to standardized coding systems and regulatory requirements:

- **ICD Codes (International Classification of Diseases):** Used to classify diagnoses and patient conditions for billing, reporting, and clinical tracking.
- **CPT Codes (Current Procedural Terminology):** Used to describe medical, surgical, and diagnostic services performed by providers.
- **Modifiers:** Additional codes that provide extra detail about procedures (e.g., laterality, repeat procedures).

Scheduling systems must ensure that appointments, encounters, and billing are compliant with these standards. This project is designed to support such workflows, making it suitable for clinics, hospitals, and health tech solutions operating in the US.

**References:**
- [CDC: ICD-10 Overview](https://www.cdc.gov/nchs/icd/icd10cm.htm)
- [AMA: CPT® (Current Procedural Terminology)](https://www.ama-assn.org/practice-management/cpt)
- [CMS: Modifiers](https://www.cms.gov/medicare/billing/modifiers)

---

## ✨ Features
- **Intelligent Rule-Based Scheduling**: Flexible rules engine evaluates custom rules for every appointment.
- **Provider & Clinic Management**: Manage provider profiles, specializations, and availability.
- **Patient & Encounter Tracking**: Maintain comprehensive patient records and clinical encounters.
- **Role-Based Access**: Supports Admin, Provider, and Patient roles.
- **Extensible Operator System**: Easily add new operators for evolving business logic.
- **RESTful API**: Clean, versionable endpoints with DTOs for integration.
- **Audit Trails & Soft Deletes**: All entities track creation/update timestamps and use soft deletes.
- **Interactive API Docs**: Built-in Swagger/OpenAPI documentation.

---

## ⚡ Quickstart
```bash
# 1. Clone the repository
$ git clone <repo-url>
$ cd smart-appointment-scheduler

# 2. Install dependencies
$ ./mvnw clean install

# 3. Configure environment variables
$ cp .env.example .env  # or create .env manually (see below)

# 4. Start the application
$ ./mvnw spring-boot:run
```

The server will start on [http://localhost:8080](http://localhost:8080).

---

## 🛠️ Tech Stack
- **Backend:** Java 21, Spring Boot 3
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA, Hibernate
- **API Docs:** Springdoc OpenAPI
- **Testing:** JUnit, Mockito

---

## 🗂️ Project Structure
```text
src/main/java/com/jatin/smart_appointment_scheduler/
├── config/           # Security and configuration classes
├── controllers/      # REST API controllers
├── dtos/             # Data Transfer Objects
├── entities/         # JPA entities
├── enums/            # Enum types
├── exceptions/       # Global exception handling
├── repositories/     # Spring Data JPA repositories
├── services/         # Business logic and rules engine
│   └── operators/    # Custom operator strategies
│   └── resolvers/    # Variable resolvers for rules
└── SmartAppointmentSchedulerApplication.java
```

---

## 🧠 Rules Engine Explained
Think of the rules engine as evaluating a series of algebraic equations—each rule is a sequence of expressions that must all be true for an appointment to be allowed. The engine can evaluate multiple, diverse rules for each appointment, allowing you to chain, combine, and customize logic for any scenario. This approach makes complex business logic both powerful and easy to understand.

**How it works:**
- Each rule is broken down into steps, like lines in an algebra problem.
- Each step is an equation or comparison (e.g., `patient.age > 18`, `appointment.day in provider.available_days`).
- The engine evaluates each step in order. If every step in every rule is true, the appointment is allowed. If any step fails, the engine blocks the action and provides a clear reason.

**Example Rule 1:**
1. `days_since_last_injection(patient) > 30`
2. `appointment.day in provider.available_days`

**Example Rule 2:**
1. `patient.hasOutstandingBalance == false`
2. `appointment.type != 'SURGERY' || provider.isSurgeon == true`

You can define as many rules as needed, each with its own steps and logic. This algebraic, step-by-step approach makes it easy to add, modify, or understand even the most complex scheduling requirements.

---

## 📚 API Overview
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Main Endpoints
| Resource                | Endpoint                        | Description                  |
|-------------------------|----------------------------------|------------------------------|
| Patient                 | `/api/patient`                  | Manage patients              |
| Provider                | `/api/provider`                 | Manage providers             |
| Clinic                  | `/api/clinic`                   | Manage clinics               |
| Appointment             | `/api/appointment`              | Manage appointments          |
| Encounter               | `/api/encounter`                | Manage encounters            |
| Provider Availability   | `/api/provider-availability`     | Manage provider availability |

#### Example: Create Appointment
```json
POST /api/appointment
{
  "patientId": 1,
  "providerId": 2,
  "clinicId": 1,
  "appointmentStart": "2024-07-01T10:00:00",
  "appointmentEnd": "2024-07-01T10:30:00",
  "status": "SCHEDULED",
  "appointmentType": "CONSULTATION",
  "notes": "First visit"
}
```

---

## 🧪 Testing
- **Run all tests:**
  ```bash
  ./mvnw test
  ```
- Unit and integration tests are located under `src/test/java/com/jatin/smart_appointment_scheduler/`

---

## 🤝 Contributing
Contributions are welcome! Please open issues or submit pull requests for improvements and bug fixes. 