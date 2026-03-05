# Task 1
This is a **Spring Boot REST API** project that provides basic arithmetic operations.
It exposes endpoints to perform mathematical calculations such as addition, subtraction, multiplication, and division.

## Technology Stack  
- Java 17+  
- Spring Boot 3+  
- Spring Security  
- JWT (JSON Web Tokens)  
- Maven  
- JDK compatible with Jakarta EE (Servlet API)
  
## Project Structure
```
src/main/java/com/nc/Task1
 ├── config
 ├── controller
 ├── security
 ├── service
 └── Task1Application.java
 src/main/resources
 └── application.properties
 .gitignore
 pom.xml
 ```
## API Endpoints
POST /auth/login  
POST /auth/refresh

GET /api/add  
GET /api/subtract  
GET /api/multiply  
GET /api/divide

## Prerequisites  
- JDK 17 or higher  
- Maven  
- Postman or any REST client (for testing API endpoints)
