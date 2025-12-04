📘 MY_DOCTOR’S_APPOINTMENT — Microservices Based Appointment Booking System

MY_DOCTOR’S_APPOINTMENT is a complete microservices-based healthcare appointment management system, built using Java, Spring Boot, Spring Cloud Microservices, Spring Security, JWT Authentication, API Gateway, Eureka Server, MySQL, Kafka, and Admin Server.

This system enables Doctor Registration, Patient Registration, Appointment Booking, Payment Validation, and Notifications through Email, SMS, and WhatsApp.

🚀 Tech Stack
Backend Technologies
✔ Java 17
✔ Spring Boot
✔ Spring Cloud (Eureka, Feign Client, API Gateway)
✔ Spring Security + JWT
✔ Spring DevTools
✔ Kafka Messaging
✔ MySQL Database
✔ Feign Client for Inter-Service Communication
✔ Admin Server (for monitoring microservices)
✔ Postman REST API (API Testing)

🧩 Microservices Architecture
The project contains 5 microservices:

1 ️⃣ Doctor Service
Purpose: Handles doctor registration and profile management.
Features
Create new doctor profile
Update doctor details
Delete doctor details
Fetch doctor details
Communicates with other services via Eureka + Feign Client

Data Examples
Doctor name
Specialization
Experience
Availability
Contact details

2 ️⃣ Patient Service
Purpose: Manages all patient-related operations.
Features
Create patient profile
Update patient details
Delete patient
Fetch patient information

Data Examples
Name
Age / Gender
Medical history
Contact details

3 ️⃣ Booking Service
Purpose: Handles appointment booking and notification system.
Flow
Patient selects a doctor and time slot
Appointment created with PENDING status
Redirects to Payment Service

After successful payment:
Booking confirmed
Notification sent to Doctor
Notification sent to Patient

Notifications through:
📧 Email
📱 SMS
💬 WhatsApp

Internal Communication
Uses Feign Client to fetch doctor & patient details
Uses Kafka topics for sending notifications asynchronously

4 ️⃣ Payment Service
Purpose: Stores and validates all payment details.
Features
Store payment transaction
Validate the status (SUCCESS / FAILED)
Update appointment after success
Sends Kafka event for booking confirmation

Stored data
Payment ID
Booking ID
Patient ID
Doctor ID
Amount
Payment method
Status

5 ️⃣ Auth Service
Purpose: Handles user authentication and authorization.
Features
Register new users
Login with JWT token
Forgot password
Update / Reset password
Role-based authentication (Doctor, Patient, Admin)
Generates JWT tokens
Validates JWT tokens in API Gateway

⚙️ Supporting Components
🛰 Eureka Server

Used for service discovery.
Each microservice registers here
Services communicate using their service names

Example:
feignClient(name="DOCTOR-SERVICE")

🚪 API Gateway
Handles:
Routing
JWT validation
Role authentication
Path-based routing to microservices
Filters to secure endpoints

📬 Kafka Messaging
Used for:
Sending notifications
Appointment status updates
Payment confirmation events

Kafka Topics Example:
appointment-created
payment-success
send-notification

🎛 Admin Server
Used for monitoring microservices:
Service health
Metrics
Memory usage
Thread monitoring
Live service status

💾 Database

Each microservice uses its own MySQL database following the Database per Service Pattern:

Service	Database
Doctor       Service	doctordb
Patient      Service	patientdb
Booking      Service	bookingdb
Payment      Service	paymentdb
Auth         Service	authdb

📡 Inter-Service Communication
1. Feign Client
Used to communicate between services:
Booking → Doctor Service
Booking → Patient Service
Booking → Payment Service

2. Kafka
Used for notification event publishing:
Payment service → Booking service
Booking service → Notification service

🔐 Security & Authentication
Security is implemented using:
Spring Security
JWT Authentication
Role-based access control
API Gateway token validation
Token Flow

User logs in from Auth Service
JWT Token generated
Token passed in header for all APIs
API Gateway validates token before routing

📮 Notifications

Notifications sent automatically upon booking success:
📧 Email (Java Mail Sender / SMTP)
📱 SMS (Twilio / FreeSMS)
💬 WhatsApp (Twilio WhatsApp API)

🧪 Testing (Postman)
All APIs tested via Postman:
Register, Login
CRUD for doctors
CRUD for patients
Book appointment
Make payment
Verify notifications
