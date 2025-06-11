# Maskany University Dormitory Management System

## Description

This project is a Spring Boot application designed to manage various aspects of a system, potentially related to university administration.It handles functionalities such as user management (with admin and regular user roles), admissions, room assignments, meal requests, and penalty tracking. It utilizes Spring Security for authentication and authorization, JPA for data persistence with MySQL, and includes features for handling data imports/exports (likely using Apache POI). The project is built with Maven and requires Java 17.



## Features

- **User Management**: Differentiated roles for administrators and regular users.
- **Admission Requests**: Handling and tracking of admission requests.
- **Room Assignment**: Management of room assignments, potentially for students or residents.
- **Meal Requests**: Functionality for requesting and managing meals.
- **Penalty Tracking**: System to track and manage penalties.
- **Data Persistence**: Utilizes MySQL database for data storage via Spring Data JPA.
- **Security**: Implements Spring Security for authentication and authorization, including JWT for token-based security.
- **Data Import/Export**: Capability to handle Excel files, likely for bulk data operations (e.g., student lists, guidelines).
- **Email Notifications**: Integration with Spring Mail for sending email notifications.



## Installation

To set up and run this project locally, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven (for building the project)
- MySQL database server

### Database Setup

1. Create a MySQL database for the project (e.g., `gp_db`).
2. Update the database connection properties in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/gp_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```
   Replace `gp_db`, `your_mysql_username`, and `your_mysql_password` with your actual database details.

### Building and Running

1. Clone the repository:
   ```bash
   git clone https://github.com/YasmeenGad/GP.git
   cd GP
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080` (or the port configured in `application.properties`).



