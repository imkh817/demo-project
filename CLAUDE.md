# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
Spring Boot 3.5.5 application using Java 21 with JWT authentication, Spring Security, MyBatis ORM, and MySQL database. The project follows a layered architecture with security, repository, and controller layers.

## Build & Run Commands

### Development
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test
```

### Testing
```bash
# Run all tests
./gradlew test

# Run tests with JUnit Platform
./gradlew test --info
```

## Architecture & Code Structure

### Security Layer
- **JWT Authentication**: Uses JJWT library (v0.11.5) for token generation/validation
- **Spring Security Configuration**: Located in `src/main/java/com/example/demo/security/config/SecurityConfig.java`
  - Stateless session management
  - Role-based access control (ADMIN, CUSTOMER, EMPLOYEE)
  - BCrypt password encoding
- **Authentication Flow**:
  - `LoginController` handles authentication endpoints
  - `CustomUserDetailsService` integrates with database user lookup
  - `UserRepository` interface with MyBatis implementation

### Data Layer
- **MyBatis ORM**: Spring Boot MyBatis starter (v3.0.5)
- **Database**: MySQL with connection pooling
- **Repository Pattern**:
  - `UserRepository` interface in `src/main/java/com/example/demo/security/repository/`
  - `MyBatisUserRepository` implementation
- **Entities**: Located in `src/main/java/com/example/demo/security/entity/`

### Configuration Files
- **Database Config**: `src/main/resources/application.properties`
  - MySQL connection: `jdbc:mysql://localhost:3306/demo_item`
  - JWT settings: 24-hour expiration (86400000ms)
- **Logging Config**: `src/main/resources/log4j2.xml`
  - Async logging with Disruptor for performance
  - File rotation (daily + 10MB size-based)
  - Separate error file logging
  - Console output with color highlighting

## Dependencies & Frameworks
- **Spring Boot 3.5.5** (Web, Security, Test)
- **JWT**: io.jsonwebtoken (API, Implementation, Jackson serialization)
- **MyBatis**: mybatis-spring-boot-starter 3.0.5
- **Database**: MySQL Connector/J
- **Logging**: Log4j2 with Disruptor (replaces default Logback)
- **Utils**: Lombok for boilerplate reduction

## Development Notes
- **Java Version**: 21 with toolchain configuration
- **Package Structure**: `com.example.demo` with security, repository, and controller subpackages
- **Security Debugging**: Spring Security TRACE logging available in log4j2.xml (commented)
- **Session Management**: Stateless (no server-side sessions)
- **CSRF**: Disabled for REST API usage