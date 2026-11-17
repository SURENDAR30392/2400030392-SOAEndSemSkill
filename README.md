# Spring Boot JWT Authentication & Role-Based Authorization (RBAC)

A secure REST API built with Spring Boot 3, Spring Security 6, Spring Data JPA, and MySQL implementing JWT (JSON Web Token) authentication, token validation, and Role-Based Access Control (RBAC).

## 🚀 Technology Stack

- **Java**: 17 / 21+
- **Framework**: Spring Boot 3.3.5
- **Security**: Spring Security 6 (Stateless Session Management)
- **Database**: MySQL 8.0
- **ORM / Persistence**: Spring Data JPA / Hibernate 6
- **JWT Library**: JJWT (`io.jsonwebtoken` 0.12.6)
- **Documentation**: Springdoc OpenAPI / Swagger UI
- **Build Tool**: Maven

---

## 📁 Project Structure

```text
com.example.jwtproject
├── JwtApplication.java           # Main application entry point & DB seed runner
├── config
│   ├── OpenApiConfig.java        # Swagger UI & Bearer Auth configuration
│   └── SecurityConfig.java       # SecurityFilterChain, CORS, and endpoint rules
├── controller
│   ├── AuthController.java       # /api/auth/register, /api/auth/login, /api/auth/profile
│   └── UserController.java       # /api/user/dashboard, /api/admin/dashboard
├── dto
│   ├── ApiResponse.java          # Standard generic API response format
│   ├── AuthResponse.java         # Token & authentication response
│   ├── ErrorResponse.java        # Standardized error payload
│   ├── LoginRequest.java         # Login credentials DTO
│   ├── RegisterRequest.java      # Registration payload DTO
│   └── UserProfileResponse.java  # Authenticated user profile DTO
├── entity
│   ├── Role.java                 # Enum: USER, ADMIN
│   └── User.java                 # JPA Entity implementing UserDetails
├── exception
│   ├── GlobalExceptionHandler.java # @RestControllerAdvice centralized error handler
│   ├── InvalidTokenException.java
│   ├── ResourceNotFoundException.java
│   └── UserAlreadyExistsException.java
├── repository
│   └── UserRepository.java       # Spring Data JPA UserRepository
├── security
│   ├── CustomAccessDeniedHandler.java    # 403 Forbidden handler
│   ├── CustomUserDetailsService.java     # UserDetailsService implementation
│   ├── JwtAuthenticationEntryPoint.java  # 401 Unauthorized handler
│   ├── JwtAuthenticationFilter.java      # OncePerRequestFilter for JWT validation
│   └── JwtService.java                   # Token generation, claims, and signature verification
└── service
    └── AuthService.java          # Authentication business logic & password encryption
```

---

## ⚙️ Database Configuration

Configure your MySQL settings in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_auth_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Kavitha@123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

application.jwt.secret=c2VjdXJlSnd0U2VjcmV0S2V5Rm9yU3ByaW5nQm9vdEF1dGhlbnRpY2F0aW9uUHJvamVjdDEyMw==
application.jwt.expiration=86400000
```

---

## 🔒 Security & Validation Rules

1. **Password Encryption**: All passwords are encrypted using BCrypt (`$2a$10$...`) before persisting to MySQL.
2. **Stateless Authentication**: Sessions are stateless (`SessionCreationPolicy.STATELESS`).
3. **Pre-Database Fast Validation**: JWT tokens are verified for signature and expiration before any database queries are executed.
4. **Error Handling**:
   - Missing or expired token: `HTTP 401 Unauthorized`
   - Tampered / invalid signature: `HTTP 401 Unauthorized`
   - Insufficient permissions (e.g. USER accessing ADMIN endpoint): `HTTP 403 Forbidden`

---

## 📌 REST Endpoints

| Method | Endpoint | Access Level | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Public | Register new user with username, email, password, and role |
| `POST` | `/api/auth/login` | Public | Authenticate user and issue signed JWT token |
| `GET` | `/api/auth/profile` | Authenticated | Retrieve current user profile details |
| `GET` | `/api/user/dashboard` | `USER`, `ADMIN` | Protected dashboard accessible to both roles |
| `GET` | `/api/admin/dashboard` | `ADMIN` Only | Administrative endpoint with system metrics |
| `GET` | `/swagger-ui/index.html` | Public | Interactive Swagger UI API documentation |
| `GET` | `/v3/api-docs` | Public | OpenAPI 3.0 specification |

---

## 📖 Swagger UI

Swagger UI is enabled at:
```
http://localhost:8080/swagger-ui/index.html
```
Click the **Authorize** button to inject the Bearer token directly.
