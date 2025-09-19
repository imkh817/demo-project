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

# Run tests with detailed output
./gradlew test --info

# Run tests continuously during development
./gradlew test --continuous

# Run a specific test class
./gradlew test --tests "com.example.demo.DemoApplicationTests"
```

## Architecture & Code Structure

### Package Structure
```
com.example.demo/
├── auth/                    # Authentication layer
│   ├── controllers/         # Login and auth endpoints
│   ├── dto/                # Authentication DTOs
│   ├── enums/              # Auth-related enums
│   └── service/            # Authentication business logic
├── user/                   # User domain
│   ├── dto/                # User DTOs
│   ├── entity/             # User entities
│   ├── enums/              # User-related enums
│   └── repository/         # User data access
├── security/               # Security infrastructure
│   ├── config/             # Security configuration
│   ├── dto/                # Security DTOs
│   ├── exception/          # Security exceptions
│   ├── filter/             # JWT filters
│   └── jwt/                # JWT token management
└── exception/              # Global exception handling
```

### Security Layer
- **JWT Authentication**: Uses JJWT library (v0.11.5) for token generation/validation
- **Spring Security Configuration**: Located in `src/main/java/com/example/demo/security/config/SecurityConfig.java`
  - Stateless session management
  - Role-based access control (ADMIN, CUSTOMER, EMPLOYEE)
  - BCrypt password encoding
  - Currently uses in-memory user (kunhee/12345/ADMIN) for testing
- **Authentication Flow**:
  - `LoginController` (`/login` POST) handles authentication
  - `AuthService` manages authentication logic
  - `JwtTokenProvider` generates access/refresh tokens
  - `JwtAuthenticationFilter` validates tokens on requests
- **Authentication Events**: `AuthenticationEvents.java` handles login success/failure event logging
- **Custom Handlers**: `CustomAuthenticationFailureHandler.java` and `CustomAccessDeniedHandler.java` for error handling

### Data Layer
- **MyBatis ORM**: Spring Boot MyBatis starter (v3.0.5)
- **Database**: MySQL with connection pooling
- **Repository Pattern**:
  - `UserRepository` interface in `src/main/java/com/example/demo/user/repository/`
  - `MyBatisUserRepository` implementation
- **Entities**: Located in `src/main/java/com/example/demo/user/entity/`
- **Mapper Configuration**:
  - XML mappers expected in `src/main/resources/mapper/` (directory not yet created)
  - Camel case conversion enabled for database column mapping

### Configuration Files
- **Database Config**: `src/main/resources/application.properties`
  - MySQL connection: `jdbc:mysql://localhost:3306/demo_item`
  - Development credentials: devuser/12345
  - JWT settings: 500000ms expiration (≈8.33 minutes)
  - MyBatis mapper locations: `classpath:mapper/*.xml`
- **Logging Config**: `src/main/resources/log4j2.xml`
  - Async logging with Disruptor for performance
  - File rotation (daily + 10MB size-based)
  - Platform-specific paths (Mac/Windows) configured
  - Separate error file logging
  - Console output with color highlighting
  - Security debugging available (commented out)

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
- **Security Debugging**: Spring Security TRACE logging currently enabled in application.properties
- **Session Management**: Stateless (no server-side sessions)
- **CSRF**: Disabled for REST API usage

## Code Modification Protocol
When the user requests any coding-related changes or implementations:
1. **FIRST**: Explain the plan and approach
2. **EXPLAIN**: Why this approach is necessary/beneficial
3. **DESCRIBE**: What will be changed and where
4. **THEN**: Wait for user confirmation before making any code changes
5. **NEVER**: Make immediate code changes without explaining the plan first

This applies to ALL coding requests including:
- Adding new features
- Fixing bugs
- Refactoring code
- Adding configurations
- Creating new classes/methods
- Modifying existing code